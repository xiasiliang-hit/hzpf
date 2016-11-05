package com.line.bqxd.platform.controller.v2.manager.claim;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.ConcurPlanQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.v2.manager.vo.ClaimApplyVO;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/manager/claim")
public class PfClaimController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PfClaimController.class);

    @Autowired
    private ClaimApplyManager claimApplyManager;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/list")
    public ModelAndView list(UserClaimApplyQueryDO userClaimApplyQueryDO, @RequestParam(value = "queryJson", required = false) String queryJson, HttpServletRequest request, Model model) throws Exception {
        if (StringUtils.isNotBlank(queryJson)) {
            UserClaimApplyQueryDO userQueryDOJson = JSON.parseObject(queryJson, UserClaimApplyQueryDO.class);
            userQueryDOJson.setPageNum(userClaimApplyQueryDO.getPageNum());
            userClaimApplyQueryDO = userQueryDOJson;
        }

        ModelAndView mav = new ModelAndView();
        AdminSession adminSession = this.getAdminSessionUserInfo(request);
        PageResult result=null;
        final long concurId = getConcurId(adminSession);
        if(concurId>0) {
            userClaimApplyQueryDO.setConcurPlanId(concurId);
            PageWrap pageWrap = new PageWrap();

            result = pageWrap.getPageResult(userClaimApplyQueryDO, new PageHandler<UserClaimApplyQueryDO>() {
                @Override
                public List<? extends Base> getPageList(UserClaimApplyQueryDO queryDO) {
                    return conver(claimApplyManager.selectByQuery(queryDO), concurId);
                }

                @Override
                public long getPageCount(UserClaimApplyQueryDO queryDO) {
                    return claimApplyManager.countByQuery(queryDO);
                }
            });
        }
        mav.addObject("page", result);
        if(result!=null) {
            if (StringUtils.isNotBlank(queryJson)) {
                result.setQueryJson(URLEncoder.encode(queryJson));
            } else {
                if (userClaimApplyQueryDO != null) {
                    result.setQueryJson(URLEncoder.encode(JSON.toJSONString(userClaimApplyQueryDO)));
                }
            }
        }
        mav.setViewName("v2/manager/claim/list");
        return mav;

    }


    @RequestMapping(value = "/view")
    public ModelAndView view(@RequestParam(value = "id", required = false) long id, HttpServletRequest request, Model model) throws Exception {

        ModelAndView mav = new ModelAndView();
        AdminSession adminSession = this.getAdminSessionUserInfo(request);
        ClaimApplyDO claimApplyDO = claimApplyManager.selectById(id);
        long concurId = claimApplyDO.getConcurPlanId();
        long userId = claimApplyDO.getUserId();
        long userConcurRelationId = claimApplyDO.getUserConcurRelationId();
        UserDO userDO = userManager.selectByUserId(userId);
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurId);
        UserConcurRelationDO userConcurRelationDO = concurManager.selectUserConcurRelation(userConcurRelationId);
        ClaimApplyVO claimApplyVO = new ClaimApplyVO();
        BeanUtils.copyProperties(claimApplyDO, claimApplyVO);
        mav.addObject("user", userDO);
        mav.addObject("claimApply", conver(claimApplyVO, claimApplyManager));
        mav.addObject("concurPlan", concurPlanDO);
        mav.addObject("userConcurRelation", userConcurRelationDO);

        mav.setViewName("v2/manager/claim/view");
        return mav;

    }

    @RequestMapping(value = "/handle")
    @ResponseBody
    public Result claimHandle(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                              HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        int status = Integer.parseInt(request.getParameter("status"));
        if (status == Status.ClaimApply.RECEIVE.getValue()) {
            claimApplyManager.receiveClaimById(id);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台受理案件成功");
        } else if (status == Status.ClaimApply.REFUSE.getValue()) {
            String verifyUserResultStr = request.getParameter("verifyUserResult");
            String verifyResultStr = request.getParameter("verifyResult");
            String result = request.getParameter("result");
            if (StringUtils.isBlank(verifyResultStr)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择审核意见");
            }
            if (StringUtils.isBlank(verifyUserResultStr)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择医生诊断信息");
            }
            if (StringUtils.isBlank(result)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请输入审核缘由");
            }
            int verifyUserResult = Integer.parseInt(verifyUserResultStr);
            int verifyResult = Integer.parseInt(verifyResultStr);
            claimApplyManager.refushClaimById(id, result, verifyUserResult, verifyResult);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台受理案件拒绝");
        } else if (status == Status.ClaimApply.ACCEPT.getValue()) {

            String verifyUserResultStr = request.getParameter("verifyUserResult");
            String verifyResultStr = request.getParameter("verifyResult");
            String result = request.getParameter("result");
            String preCollectMoneyStr = request.getParameter("preCollectMoney");

            if (StringUtils.isBlank(preCollectMoneyStr)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择输入预筹款金额");
            }
            long preCollectMoney = 0;
            try {
                preCollectMoney = Long.parseLong(preCollectMoneyStr);
            } catch (Exception e) {
            }
            ;
            if (preCollectMoney <= 0) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择输入正确预筹款金额");
            }
            if (StringUtils.isBlank(verifyResultStr)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择审核意见");
            }
            if (StringUtils.isBlank(verifyUserResultStr)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请选择医生诊断信息");
            }
            if (StringUtils.isBlank(result)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请输入审核缘由");
            }

            int verifyUserResult = Integer.parseInt(verifyUserResultStr);
            int verifyResult = Integer.parseInt(verifyResultStr);

            claimApplyManager.acceptClaimById(id, result, verifyUserResult, verifyResult, preCollectMoney * 100);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台受理理赔案件成功");

        } else if (status == Status.ClaimApply.PUBLISH.getValue()) {
            claimApplyManager.publishClaimById(id);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台理赔案件公示成功");
        } else if (status == Status.ClaimApply.COLLECTEND.getValue()) {
            claimApplyManager.collectingClaimById(id);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台理赔案件筹款提交成功,等待筹款结果");
        } else if (status == Status.ClaimApply.BANDY.getValue()) {
            claimApplyManager.bandyClaimById(id);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台理赔案件开始打款");
        } else if (status == Status.ClaimApply.END.getValue()) {
            String bankProofPic = request.getParameter("bankProofPic");
            if (StringUtils.isBlank(bankProofPic)) {
                return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc(), "请输入银行汇款凭证");
            }
            claimApplyManager.endClaimById(id, bankProofPic);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台理赔案件结束");
        } else if (status == Status.ClaimApply.OPPUGN.getValue()) {
            claimApplyManager.closeClaimById(id);
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), "平台理赔案件关闭");
        } else if (status == Status.ClaimApply.CLOSE.getValue()) {

        }

        return null;
    }

    public static ClaimApplyVO conver(ClaimApplyVO claimApplyDO, ClaimApplyManager claimApplyManager) {
        String str = claimApplyDO.getPicArray();
        if (StringUtils.isNotBlank(str)) {
            String[] array = str.split(";");
            List<String> list = new ArrayList<String>();
            if (array != null && array.length > 0) {
                for (String pic : array) {
                    if (StringUtils.isNotBlank(pic)) {
                        list.add(claimApplyManager.getDownloadPicPrefix() + pic);
                    }
                }
                claimApplyDO.setPicUrl(list);
            }
        }

        return claimApplyDO;
    }

    private List<ClaimApplyVO> conver(List<ClaimApplyDO> list, long concurId) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> ids = new ArrayList<Long>();
        for (ClaimApplyDO claimApplyDO : list) {
            ids.add(claimApplyDO.getUserConcurRelationId());
        }
        List<Long> userIds = new ArrayList<Long>();


        List<UserConcurRelationDO> findList = concurManager.selectUserConcurRelationByIdS(ids);
        Map<Long, UserConcurRelationDO> map = new HashMap<Long, UserConcurRelationDO>();
        if (findList != null) {
            for (UserConcurRelationDO userConcurRelationDO : findList) {
                map.put(userConcurRelationDO.getId(), userConcurRelationDO);
                userIds.add(userConcurRelationDO.getUserId());
            }
        }
        Map<Long, UserDO> userMap = userManager.selectByUserIds(userIds);

        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurId);
        List<ClaimApplyVO> rtList = new ArrayList<ClaimApplyVO>();
        for (ClaimApplyDO claimApplyDO : list) {
            ClaimApplyVO claimApplyVO = new ClaimApplyVO();
            BeanUtils.copyProperties(claimApplyDO, claimApplyVO);
            claimApplyVO.setApplyClaimMoney(concurPlanDO.getEnsureMoney());
            UserConcurRelationDO userConcurRelationDO = map.get(claimApplyDO.getUserConcurRelationId());
            if (userConcurRelationDO != null) {
                claimApplyVO.setEnsureName(userConcurRelationDO.getEnsureName());
            }
            UserDO userDO=userMap.get(userConcurRelationDO.getUserId());
            if(userDO!=null){
                claimApplyVO.setUserName(userDO.getUserName());
            }
            rtList.add(claimApplyVO);
        }
        return rtList;
    }


    private long getConcurId(AdminSession adminSession) {
        PfWeixinAuthDO pfWeixinAuthDO = pfWeixinAuthManager.selectByCorpid(adminSession.getCorpId());
        if(pfWeixinAuthDO==null){
            return 0;
        }
        long pfAppId = pfWeixinAuthDO.getId();
        ConcurPlanQueryDO queryDO = new ConcurPlanQueryDO();
        queryDO.setPfAppId(pfAppId);
        List<ConcurPlanDO> list = concurManager.selectByQueryPage(queryDO);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return list.get(0).getId();
        }
    }

}
