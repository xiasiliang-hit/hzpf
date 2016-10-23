package com.line.bqxd.platform.controller.v2.concur;

import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.controller.admin.domain.UserConcurRelationVO;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.domain.response.MyResponse;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.dunk.DunkBillManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wx.WXManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/16.
 */
@Controller
@RequestMapping("/v2/concur")
public class ConcurMyController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ConcurMyController.class);

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private WXManager wxManager;

    @Autowired
    private ClaimApplyManager claimApplyManager;
    @Autowired
    private DunkBillManager dunkBillManager;

    @RequestMapping("/my")
    public ModelAndView index(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, Model model) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        List<UserConcurRelationDO> list = concurManager.getUserConcurRelation(sessionUserInfo.getUserId(), concurPlanId);
        ConcurPlanDO concurPlanDO=concurManager.getConcurPlanById(concurPlanId);
        ModelAndView mav = new ModelAndView();
        UserDO userDO = userManager.selectByUserId(sessionUserInfo.getUserId());
        MyResponse myResponse = new MyResponse();
        myResponse.setConcurPlanDO(concurPlanDO);
        myResponse.setUserDO(userDO);
        if (list == null||list.size()==0) {
            logger.warn("user not join concur plan userId={},userName={}", sessionUserInfo.getUserId(), sessionUserInfo.getUserName());
            mav.addObject("errorCode", "notJoin");
            mav.addObject("errorDesc", "用户还没有加入互助计划");
        } else {
/*
            if (userDO.() == false)
            {
                mav.setViewName("/v2/concur/addStep2");
            }
*/
            try {
                List<UserConcurRelationVO> voList=conver(list);
                myResponse.setUserList(voList);
                myResponse.setApplyConcur(isApplyConcur(voList));
            } catch (Exception e) {
                logger.error("ConcurMyController index error", e);
            }
        }
        handleQuantity(myResponse,concurPlanId);
        mav.addObject("myConcur", myResponse);
        setSignView(mav, request, sessionUserInfo.getAppid());
        mav.setViewName("/v2/concur/my");
        return mav;
    }


    private boolean isApplyConcur(List<UserConcurRelationVO> list){
        boolean isApplyConcur=false;
        if(!CollectionUtils.isEmpty(list)){
            for(UserConcurRelationVO relationVO:list){
                if("保障中".equals(relationVO.getEnsureDesc())){
                    isApplyConcur=true;
                    break;
                }
                if(relationVO.getEnsureStatus()==Status.EnsureStatus.CLIAMREFUSE.getValue()){
                    isApplyConcur=true;
                    break;
                }
            }
        }
        return isApplyConcur;

    }

    private void handleQuantity(MyResponse myResponse,long concurPlanId){
        long userId=myResponse.getUserDO().getUserId();
        long concurBalanceQuantity = concurManager.getConcurPlanMoneySumQuantity(concurPlanId);
        long concurUserQuantity = concurManager.getConcurPlanSumUserQuantity(concurPlanId);
        long userBalance=userManager.getBalance(userId,concurPlanId);

        myResponse.setConcurBalanceQuantity(concurBalanceQuantity);
        myResponse.setConcurUserQuantity(concurUserQuantity);
        myResponse.setUserBalanceQuantity(userBalance);


        myResponse.setConcurEventQuantity(claimApplyManager.gtCountClaimSuccess(concurPlanId));
        myResponse.setUserDonateBalanceQuantity(dunkBillManager.statisticsConsume(userId,concurPlanId));
    }


    private List<UserConcurRelationVO> conver(List<UserConcurRelationDO> list)throws Exception{
        List<UserConcurRelationVO> voList= new ArrayList<UserConcurRelationVO>();
        for(UserConcurRelationDO relationDO:list){
            UserConcurRelationVO vo =new UserConcurRelationVO();
            BeanUtils.copyProperties(relationDO,vo);
            getDesc(vo);
            voList.add(vo);
        }

        return voList;
    }

    private void getDesc(UserConcurRelationVO vo) throws Exception {
        int birthDays = (int) DateUtils.daysBetween(vo.getBirthDay(),
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), DateUtils.DATA_FORMAT2) + 1;
         if (birthDays < 365) {

            vo.setAgeDesc(birthDays + "天");
        } else {
            int age = birthDays / 365;
            vo.setAgeDesc(age + "");
        }

        int between = (int) DateUtils.daysBetween(
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), vo.getEnsureStartTime(), DateUtils.DATA_FORMAT2);
        if(vo.getEnsureStatus()== Status.EnsureStatus.CLIAM.getValue()){
            vo.setEnsureDesc("提交审核中");
        }else if (vo.getEnsureStatus()== Status.EnsureStatus.CLIAMREFUSE.getValue()) {
            vo.setEnsureDesc("理赔拒绝");
        }else if (between <= 0) {
            vo.setEnsureDesc("保障中");
        } else {
            String formatDay = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT1, DateUtils.Convert(vo.getEnsureStartTime()));
            vo.setEnsureDesc("等待期还剩" + between + "天，至" + formatDay);
        }

        vo.setUpperLimitYuan(BizUtils.changeF2Y(vo.getUpperLimit()));
        vo.setRatioFormat(BizUtils.changeF2Y(vo.getRatio()));
    }




}
