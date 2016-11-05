package com.line.bqxd.platform.controller.v2.concur;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.IDCard;
import com.line.bqxd.platform.controller.admin.domain.UserConcurRelationVO;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.v2.domain.response.ClaimApplyResponse;
import com.line.bqxd.platform.controller.v2.manager.claim.PfClaimController;
import com.line.bqxd.platform.controller.v2.manager.vo.ClaimApplyVO;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.claim.impl.ClaimApplyManagerImpl;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.dunk.DunkBillManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import com.line.bqxd.platform.v2.PfApplication;
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
import java.text.ParseException;
import java.util.*;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/claim")
public class ClaimController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ClaimController.class);

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ClaimApplyManager claimApplyManager;

    @Autowired
    private DunkBillManager dunkBillManager;

    @RequestMapping(value = "/choose")
    public ModelAndView choose(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        SessionUserInfo userInfo = getSessionUserInfo(request);
        UserDO userDO = userManager.selectByUserId(userInfo.getUserId());
        List<UserConcurRelationDO> list = concurManager.getCanCliamUserConcurRelation(userInfo.getUserId(), concurPlanId);
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        List<UserConcurRelationVO> voList = conver(list);
        String joinTime = voList.get(0).getJoinTime();
        joinTime = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT3, DateUtils.parse(joinTime, DateUtils.DATA_FORMAT2));
        DateUtils.parse(joinTime, DateUtils.DATA_FORMAT3);
        mav.addObject("joinTime", joinTime);
        mav.addObject("list", voList);
        mav.addObject("userDO", userDO);
        mav.addObject("concurPlan", concurPlanDO);
        mav.setViewName("/v2/claim/choose");
        setSignView(mav, request, userInfo.getAppid());
        return mav;

    }

    @RequestMapping(value = "/apply")
    public ModelAndView apple(@RequestParam(value = "relationId", required = false, defaultValue = "0") long relationId, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        SessionUserInfo userInfo = getSessionUserInfo(request);
        UserClaimApplyQueryDO queryDO = new UserClaimApplyQueryDO();
        queryDO.setUserConcurRelationId(relationId);
        List<ClaimApplyDO> list = claimApplyManager.selectByQuery(queryDO);
        if (!CollectionUtils.isEmpty(list)) {
            for (ClaimApplyDO claimApplyDO : list) {
                if (claimApplyDO.getStatus() != Status.ClaimApply.REFUSE.getValue()) {
                    errorRedirect(request, response, "已经提交过互助申请");
                }
            }
        }
        UserConcurRelationDO relationDO = concurManager.selectUserConcurRelation(relationId);
        UserDO userDO = userManager.selectByUserId(userInfo.getUserId());
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(relationDO.getConcurPlanId());
        mav.addObject("relationId", relationId);
        mav.addObject("userDO", userDO);
        mav.addObject("concurPlan", concurPlanDO);
        mav.setViewName("/v2/claim/apply");
        setSignView(mav, request, userInfo.getAppid());
        return mav;

    }

    @RequestMapping(value = "/applySubmit")
    @ResponseBody
    public Result applySubmit(@RequestParam(value = "relationId", required = false, defaultValue = "0") long relationId,
                              ClaimApplyDO claimApplyDO,
                              HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        SessionUserInfo userInfo = getSessionUserInfo(request);
        filter(claimApplyDO);
        String bankNo = request.getParameter("bankNo");
        String bankName = request.getParameter("bankName");
        ResultEnum resultEnum = verify(claimApplyDO,bankNo,bankName);
        if (resultEnum != ResultEnum.SUCCESS) {
            return Result.of(resultEnum.getCode(), resultEnum.getDesc());
        }

        UserConcurRelationDO relationDO = concurManager.selectUserConcurRelation(relationId);
        claimApplyDO.setConcurPlanId(relationDO.getConcurPlanId());
        claimApplyDO.setUserConcurRelationId(relationId);
        claimApplyDO.setUserId(userInfo.getUserId());
        claimApplyDO.setUserName(userInfo.getUserName());
        try {
            userManager.perfectUserBank(userInfo.getUserId(),bankNo,bankName);
            boolean rt = claimApplyManager.insert(claimApplyDO);
            concurManager.claimUserRelation(relationDO.getId());
            if (rt) {
                concurManager.claimUserRelation(relationDO.getId());
                return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc());
            } else {
                return Result.of(ResultEnum.CLAIM_SUBMIT_FAIL.getCode(), ResultEnum.CLAIM_SUBMIT_FAIL.getDesc());
            }
        } catch (Exception e) {
            logger.error("applySubmit fail relationId=" + relationId + ",claimApplyDO=" + claimApplyDO, e);
            return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
        }

    }

    private ResultEnum verifyBank(String bankNo, String bankName) {

        if (StringUtils.isBlank(bankName)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NAME_BLANK;
        }

        char[] ch = bankName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!IDCard.isChinese(c)) {
                return ResultEnum.CLAIM_VERIFY_BANK_NAME_FAIL;
            }
        }

        if (bankName.length() < 4) {
            return ResultEnum.CLAIM_VERIFY_BANK_NAME_FAIL;
        }

        if (StringUtils.isBlank(bankNo)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_BLANK;
        }
        if (!IDCard.isNumeric(bankNo)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_FAIL;
        }
        if (bankNo.length() < 16) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_FAIL;
        }
        return ResultEnum.SUCCESS;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "concurPlanId", required = true, defaultValue = "0") final long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 1;
        if (StringUtils.isNotBlank(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
            if (pageNum <= 0) {
                pageNum = 1;
            }
        }
        final SessionUserInfo userInfo = getSessionUserInfo(request);
        UserClaimApplyQueryDO queryDO = new UserClaimApplyQueryDO();
        queryDO.setConcurPlanId(concurPlanId);
        queryDO.setPageNum(pageNum);
        queryDO.setStatusList(ClaimApplyManagerImpl.PUSHLISTSTATUS);
        PageWrap pageWrap = new PageWrap();
        PageResult result = pageWrap.getPageResult(queryDO, new PageHandler<UserClaimApplyQueryDO>() {
            @Override
            public List<? extends Base> getPageList(UserClaimApplyQueryDO queryDO) {
                List<ClaimApplyDO> publishList = claimApplyManager.selectByQuery(queryDO);
                UserClaimApplyQueryDO selfQueryDO = new UserClaimApplyQueryDO();
                selfQueryDO.setConcurPlanId(concurPlanId);
                selfQueryDO.setUserId(userInfo.getUserId());
                selfQueryDO.setStatusList(ClaimApplyManagerImpl.NOPUSHLISTSTATUS);
                List<ClaimApplyDO> selfNoPublishList = claimApplyManager.selectByQuery(selfQueryDO);
                List<ClaimApplyDO> allList = new ArrayList<ClaimApplyDO>();
                if (!CollectionUtils.isEmpty(publishList)) {
                    allList.addAll(publishList);
                }
                if (!CollectionUtils.isEmpty(selfNoPublishList)) {
                    allList.addAll(selfNoPublishList);
                }
                return conver(allList, concurPlanId);
            }

            @Override
            public long getPageCount(UserClaimApplyQueryDO queryDO) {
                return claimApplyManager.countByQuery(queryDO);
            }
        });
        mav.addObject("page", result);

        mav.setViewName("/v2/claim/list");
        setSignView(mav, request, userInfo.getAppid());
        return mav;

    }

    private List<ClaimApplyResponse> conver(List<ClaimApplyDO> list, long concurPlanId) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        if (concurPlanDO == null) {
            return null;
        }
        //UserDO userDO=userManager.selectByUserId(userId);
        List<ClaimApplyResponse> rtList = new ArrayList<ClaimApplyResponse>();
        for (ClaimApplyDO claimApplyDO : list) {
            ClaimApplyResponse response = new ClaimApplyResponse();
            BeanUtils.copyProperties(claimApplyDO, response);
            long userId = response.getUserId();
            long userConcurRelationId = response.getUserConcurRelationId();
            UserDO userDO = userManager.selectByUserId(userId);
            UserConcurRelationDO relationDO = concurManager.selectUserConcurRelation(userConcurRelationId);
            if (userDO == null) {
                continue;
            }
            response.setUserName(userDO.getUserName());
            response.setUserPicUrl(userDO.getHeadImgurl());
            response.setEnsureName(relationDO.getEnsureName());
            response.setFirstPicUrl(getFirstPic(response.getPicArray(), claimApplyManager.getDownloadPicPrefix()));
            if (response.getStatus() == Status.ClaimApply.PUBLISH.getValue()) {
                response.setPublishDays(getPushlishDay(response.getPublishStartDay())+1);
            }
            if(response.getDescr().length()>80){
                response.setDescr(response.getDescr().substring(0,80));
            }

            setTitle(response);
            rtList.add(response);
        }
        return rtList;
    }

    private static void setTitle(ClaimApplyResponse response) {
        if (response.getMonthIndex() > 0) {
            //2016年7月第1起互助事件
            String eventSubmitDay = response.getEventSubmitDay();
            StringBuilder sb = new StringBuilder();
            sb.append(eventSubmitDay.substring(0, 4));
            sb.append("年");
            sb.append(eventSubmitDay.substring(5, 6));
            sb.append("月第");
            sb.append(response.getMonthIndex());
            sb.append("起互助事件");
            response.setTitle(sb.toString());
        } else {
            response.setTitle("你的申请互助在后台审核中");
        }
    }

    private static int getPushlishDay(String pushlishDay) {
        if (StringUtils.isBlank(pushlishDay)) {
            return 0;
        }
        try {
            int between = (int) DateUtils.daysBetween(pushlishDay,
                    DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), DateUtils.DATA_FORMAT2);
            return between;
        } catch (ParseException e) {

        }
        return 0;
    }

    private static String getFirstPic(String pics, String prePic) {
        if (StringUtils.isBlank(pics)) {
            return null;
        }
        String[] arrays = pics.split(";");
        if (arrays != null && arrays.length > 0) {
            for (String pic : arrays) {
                if (StringUtils.isNotBlank(pic)) {
                    return prePic + pic;
                }
            }
        }
        return null;
    }

    private ResultEnum verify(ClaimApplyDO claimApplyDO,String bankNo, String bankName) {

        if (StringUtils.isBlank(claimApplyDO.getVerifyHospital())) {
            return ResultEnum.CLAIM_VERIFY_HOSPITAL_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyTime())) {
            return ResultEnum.CLAIM_VERIFY_TIME_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyUser())) {
            return ResultEnum.CLAIM_VERIFY_USER_BLANK;
        }
        if (claimApplyDO.getVerifyEventId() <= 0) {
            return ResultEnum.CLAIM_VERIFY_EVENT_ID_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyTel())) {
            return ResultEnum.CLAIM_VERIFY_TEL_FAIL;
        } else {
            if (claimApplyDO.getVerifyTel().length() < 7) {
                return ResultEnum.CLAIM_VERIFY_TEL_FAIL;
            }
        }
        ResultEnum resultEnum=verifyBank(bankNo,bankName);
        if(resultEnum!=ResultEnum.SUCCESS){
            return resultEnum;
        }
        if (StringUtils.isBlank(claimApplyDO.getDescr())) {
            return ResultEnum.CLAIM_DESCR_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getPicArray())) {
            return ResultEnum.CLAIM_PIC_UPLOAD_BLANK;
        }
        return ResultEnum.SUCCESS;
    }


    private static void filter(ClaimApplyDO claimApplyDO) {
        if (claimApplyDO == null) {
            return;
        }
        if (claimApplyDO.getPicArray() != null) {
            claimApplyDO.setPicArray(claimApplyDO.getPicArray().trim());
        }
        if (claimApplyDO.getVerifyTime() != null) {
            claimApplyDO.setVerifyTime(claimApplyDO.getVerifyTime().trim().replaceAll("-", ""));
        }
        if (claimApplyDO.getVerifyHospital() != null) {
            claimApplyDO.setVerifyHospital(claimApplyDO.getVerifyHospital().trim());
        }
        if (claimApplyDO.getVerifyTel() != null) {
            claimApplyDO.setVerifyTel(claimApplyDO.getVerifyTel().trim());
        }
        if (claimApplyDO.getVerifyUser() != null) {
            claimApplyDO.setVerifyUser(claimApplyDO.getVerifyUser().trim());
        }
        if (claimApplyDO.getDescr() != null) {
            claimApplyDO.setDescr(claimApplyDO.getDescr().trim());
        }
        return;
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam(value = "serverId", required = true) String serverId,
                         ClaimApplyDO claimApplyDO,
                         HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        SessionUserInfo userInfo = getSessionUserInfo(request);
        if (StringUtils.isBlank(serverId)) {
            return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
        }
        String fileName = claimApplyManager.downloadPic(userInfo.getAppid(), serverId);
        if (StringUtils.isNotBlank(fileName)) {
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), fileName);
        } else {
            return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
        }

    }

    private List<UserConcurRelationVO> conver(List<UserConcurRelationDO> list) throws Exception {
        List<UserConcurRelationVO> voList = new ArrayList<UserConcurRelationVO>();
        if(!CollectionUtils.isEmpty(list)) {
            for (UserConcurRelationDO relationDO : list) {
                UserConcurRelationVO vo = new UserConcurRelationVO();
                BeanUtils.copyProperties(relationDO, vo);
                int between = (int) DateUtils.daysBetween(
                        DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), vo.getEnsureStartTime(), DateUtils.DATA_FORMAT2);
                if(vo.getEnsureStatus()==Status.EnsureStatus.CLIAMREFUSE.getValue()){
                    vo.setCanApply(true);
                }else if (between > 0) {
                    vo.setCanApply(false);
                } else {
                    vo.setCanApply(true);
                }
                getAge(vo);
                voList.add(vo);
            }
        }
        return voList;
    }

    private void getAge(UserConcurRelationVO vo) throws Exception {
        int birthDays = (int) DateUtils.daysBetween(vo.getBirthDay(),
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), DateUtils.DATA_FORMAT2) + 1;
        if (birthDays < 365) {
            vo.setAgeDesc(birthDays + "天");
        } else {
            int age = birthDays / 365;
            vo.setAgeDesc(age + "");
        }
    }


    @RequestMapping(value = "/view")
    public ModelAndView view(@RequestParam(value = "id", required = true, defaultValue = "0") final long id, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        final SessionUserInfo userInfo = getSessionUserInfo(request);
        ClaimApplyDO claimApplyDO = claimApplyManager.selectById(id);
        if (claimApplyDO == null) {
            errorRedirect(request, response, "没有找到公示互助");
        }
        UserDO claimUser = userManager.selectByUserId(claimApplyDO.getUserId());
        if (claimUser == null) {
            errorRedirect(request, response, "申请互助用户不存在");
        }
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(claimApplyDO.getConcurPlanId());
        if (concurPlanDO == null) {
            errorRedirect(request, response, "互助计划不存在");
        }
        long userConcurRelationId = claimApplyDO.getUserConcurRelationId();
        UserConcurRelationDO relationDO = concurManager.selectUserConcurRelation(userConcurRelationId);
        UserConcurRelationVO relationVO = new UserConcurRelationVO();
        BeanUtils.copyProperties(relationDO, relationVO);
        getAge(relationVO);
        List<UserConcurRelationVO> relationVOList = getUserCouncurList(claimApplyDO.getId(), userInfo.getUserId());
        ClaimApplyVO claimApplyVO = new ClaimApplyVO();
        BeanUtils.copyProperties(claimApplyDO, claimApplyVO);
        claimApplyVO = PfClaimController.conver(claimApplyVO, claimApplyManager);

        mav.addObject("claimApply", setValue(claimApplyVO));
        mav.addObject("claimUser", claimUser);
        mav.addObject("concurPlan", concurPlanDO);
        mav.addObject("relationVO", relationVO);
        mav.addObject("relationVOList", relationVOList);
        mav.setViewName("/v2/claim/view");
        setSignView(mav, request, userInfo.getAppid());
        return mav;

    }

    private ClaimApplyVO setValue(ClaimApplyVO claimApplyVO) {
        if(StringUtils.isNotBlank(claimApplyVO.getPublishStartDay())) {
            claimApplyVO.setPublishDays(getPushlishDay(claimApplyVO.getPublishStartDay()) + 1);
            Date publishDate = DateUtils.Convert(claimApplyVO.getPublishStartDay());
            Date publishEenDate = DateUtils.getLeaveDay(publishDate, 7);
            claimApplyVO.setPushlishEndDay(DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT1, publishEenDate));
        }
        return claimApplyVO;
    }

    private List<UserConcurRelationVO> getUserCouncurList(long claimId, long userId) {
        List<UserConcurRelationVO> rtList = new ArrayList<UserConcurRelationVO>();

        DunkBillQueryDO queryDO = new DunkBillQueryDO();
        queryDO.setUserClaimId(claimId);
        queryDO.setUserId(userId);
        queryDO.setPageSize(100);
        List<DunkBillDO> dunkList = dunkBillManager.selectByQuery(queryDO);
        if (CollectionUtils.isEmpty(dunkList)) {
            return null;
        }
        List<Long> dunkIds = new ArrayList<Long>();
        Map<Long, DunkBillDO> map = new HashMap<Long, DunkBillDO>();
        for (DunkBillDO dunkBillDO : dunkList) {
            dunkIds.add(dunkBillDO.getUserConcurRelationId());
            map.put(dunkBillDO.getUserConcurRelationId(), dunkBillDO);
        }

        List<UserConcurRelationDO> list = concurManager.selectUserConcurRelationByIdS(dunkIds);
        if (!CollectionUtils.isEmpty(list)) {
            for (UserConcurRelationDO userConcurRelationDO : list) {
                UserConcurRelationVO userConcurRelationVO = new UserConcurRelationVO();
                BeanUtils.copyProperties(userConcurRelationDO, userConcurRelationVO);
                try {
                    userConcurRelationVO.setRatioFormat(BizUtils.changeF2Y(userConcurRelationVO.getRatio()));
                } catch (Exception e) {
                }
                DunkBillDO dunkBillDO = map.get(userConcurRelationDO.getId());
                if (dunkBillDO != null) {
                    userConcurRelationVO.setRealDunkMoney(dunkBillDO.getBalance());
                }
                rtList.add(userConcurRelationVO);
            }
        }

        return rtList;
    }

}
