package com.line.bqxd.platform.controller.v2.concur;

import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.constant.UserType;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.IDCard;
import com.line.bqxd.platform.common.utils.NginxTool;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.common.VerifyException;
import com.line.bqxd.platform.controller.domain.response.PayVO;
import com.line.bqxd.platform.dao.RegMobileTempDAO;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.sms.SmsBizDO;
import com.line.bqxd.platform.manager.sms.SmsManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wx.TemplateMsgManager;
import com.line.bqxd.platform.manager.wxpay.RequestHandler;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by huangjianfei on 16/5/16.
 */
@Controller
@RequestMapping("/v2/concur")
public class ConcurController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ConcurController.class);

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private RegMobileTempDAO<RegMobileTempDO> regMobileTempDAO;

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private WXPayManager wxPayManager;

    @Autowired
    private TemplateMsgManager templateMsgManager;

    @Autowired
    private ClaimApplyManager claimApplyManager;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    private static final String[] NUMBER_CONVER = new String[]{"一", "二", "三"};


    @RequestMapping("/detail")
    public ModelAndView detailConcurl(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                      HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        ModelAndView mav = new ModelAndView();

        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);

        if (concurPlanDO == null) {
            mav.setViewName("error");
        } else {
            long balanceQuantity = concurManager.getConcurPlanMoneySumQuantity(concurPlanId);
            long userQuantity = concurManager.getConcurPlanSumUserQuantity(concurPlanId);
            long eventQuantity = claimApplyManager.gtCountClaimSuccess(concurPlanId);
            mav.addObject("balanceQuantity", balanceQuantity);
            mav.addObject("userQuantity", userQuantity);
            mav.addObject("eventQuantity", eventQuantity);
            if (concurManager.isUserEXSIT(sessionUserInfo.getUserId(), concurPlanId)) {
                mav.addObject("alreadyJoin", "true");
                /*
                if (wxManager.isUserAttention(sessionUserInfo.getOpenid())) {
                    mav.addObject("wxAttention", "true");
                } else {
                    mav.addObject("wxAttention", "false");
                }
                */

            } else {
                mav.addObject("alreadyJoin", "false");
            }
        }
        setSignView(mav, request, sessionUserInfo.getAppid());

        mav.addObject("concurPlan", concurPlanDO);
        mav.setViewName("v2/concur/detail");
        return mav;
    }

    @RequestMapping("/addChild")
    public ModelAndView addChild(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                 HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView();
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        List<Integer> status = new ArrayList<Integer>();
        status.add(Status.EnsureStatus.NEW.getValue());
        UserConcurRelationQueryDO queryDO = new UserConcurRelationQueryDO();
        queryDO.setConcurPlanId(concurPlanId);
        queryDO.setUserId(sessionUserInfo.getUserId());

        queryDO.setEnsureStatusList(status);
        if(logger.isDebugEnabled()){
            logger.debug("addChild query={}",queryDO);
        }
        List<UserConcurRelationDO> relationDOList=concurManager.selectByQueryPage(queryDO);
        if(!CollectionUtils.isEmpty(relationDOList)){
            for(UserConcurRelationDO userConcurRelationDO:relationDOList){
                concurManager.delUserRelation(userConcurRelationDO.getId());
            }
        }
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        mav.addObject("concurPlan", concurPlanDO);
        mav.addObject("user",userManager.selectByUserId(sessionUserInfo.getUserId()));
        mav.setViewName("v2/concur/addChild");
        return mav;
    }

    @RequestMapping("/addChildAction")
    @ResponseBody
    public Result addChildAction(@RequestParam("card") String card, @RequestParam("userName") String userName, @RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                 HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        if (userName != null) {
            userName = userName.trim();
        }
        if (card != null) {
            card = card.trim();
        }
        ResultEnum resultEnum = IDCard.isChinese(userName);
        if (resultEnum == ResultEnum.SUCCESS) {
            resultEnum = IDCard.IDCardValidate(card);
        }

        if (resultEnum == ResultEnum.SUCCESS) {
            UserDO alreadyUser = userManager.selectByCard(card);
            if (alreadyUser != null && alreadyUser.getUserId() != sessionUserInfo.getUserId()) {
                resultEnum = ResultEnum.BIZ_CARD_ALREADY_REG_FAIL;
            } else {
                resultEnum = ResultEnum.SUCCESS;
            }
        }
        List<UserConcurRelationDO> list = null;
        if (resultEnum == ResultEnum.SUCCESS) {
            try {
                list = getListDynByForm(request, concurPlanId, sessionUserInfo.getUserId());
            } catch (VerifyException e) {
                logger.error("USER_BIRTH_WEIGHT_POINT_UNUSUAL fail,index=" + e.getIndex(), e);
                return Result.of(ResultEnum.USER_BIRTH_WEIGHT_POINT_UNUSUAL.getCode(), "第" + NUMBER_CONVER[e.getIndex()-1] + "个小孩" + ResultEnum.USER_BIRTH_WEIGHT_POINT_UNUSUAL.getDesc());
            }
            for (int index = 0; index < list.size(); index++) {
                UserConcurRelationDO userConcurRelationDO = list.get(index);
                filter(userConcurRelationDO);
                resultEnum = verifyEnsureData(userConcurRelationDO);
                if (resultEnum != ResultEnum.SUCCESS) {
                    return Result.of(resultEnum.getCode(), "第" + NUMBER_CONVER[index] + "个小孩" + resultEnum.getDesc());
                }
            }
        }

        if (resultEnum != ResultEnum.SUCCESS) {
            return Result.of(resultEnum.getCode(), resultEnum.getDesc());
        }

        try {
            userManager.perfectUser(sessionUserInfo.getUserId(), card, userName, null);
            BizResult<List<Long>> bizResult = concurManager.addConcur(concurPlanId, userManager.selectByUserId(sessionUserInfo.getUserId()), list);
            if (bizResult != null && bizResult.isSuccess()) {
                return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc());
            } else {
                logger.error("add curcon fail,{}", bizResult);
            }
        } catch (Exception e) {
            logger.error("add curcon error", e);
        }
        return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
    }

    private List<UserConcurRelationDO> getListDynByForm(HttpServletRequest request, long concurPlanId, long userId) {
        List<UserConcurRelationDO> list = new ArrayList<UserConcurRelationDO>();
        Enumeration<String> enumeration = request.getParameterNames();
        int max = 1;
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            if (name.startsWith("ensureName")) {
                int index = Integer.parseInt(name.substring(name.length() - 1, name.length()));
                if (index > max) {
                    max = index;
                }
            }
        }
        for (int i = 1; i <= max; i++) {
            long weight = 0;
            String ensureName = request.getParameter("ensureName" + i);
            int childWeek = Integer.parseInt(request.getParameter("childWeek" + i));
            String weightstr = request.getParameter("weight" + i);
            if(!isInteger(weightstr)){
                throw new VerifyException("不要输入小数点",i);
            }
            if (StringUtils.isNotBlank(weightstr)) {
                weight = Long.parseLong(weightstr);
            }
            String birthDay = request.getParameter("birthDay" + i);
            UserConcurRelationDO userConcurRelationDO = new UserConcurRelationDO();

            userConcurRelationDO.setEnsureName(ensureName);
            userConcurRelationDO.setBirthDay(birthDay);
            userConcurRelationDO.setWeight(weight);
            userConcurRelationDO.setChildWeek(childWeek);
            userConcurRelationDO.setConcurPlanId(concurPlanId);
            userConcurRelationDO.setUserId(userId);
            userConcurRelationDO.setUserType(UserType.SUBUSER.getValue());
            list.add(userConcurRelationDO);
        }
        return list;
    }

    private static void filter(UserConcurRelationDO userRequest) {
        if (userRequest == null) {
            return;
        }
        if (userRequest.getEnsureName() != null) {
            userRequest.setEnsureName(userRequest.getEnsureName().trim());
        }
        if (userRequest.getBirthDay() != null) {
            userRequest.setBirthDay(userRequest.getBirthDay().trim().replaceAll("-", ""));
        }
        return;
    }

    private static ResultEnum verifyEnsureData(UserConcurRelationDO userRequest) {
        ResultEnum resultEnum = IDCard.isChinese(userRequest.getEnsureName());
        if (resultEnum == ResultEnum.SUCCESS) {
            int weight = userRequest.getChildWeek();
            if (weight == 0) {
                resultEnum = ResultEnum.USER_BIRTH_WEEK_BANK;
            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            long weight = userRequest.getWeight();
            if (weight == 0) {
                return resultEnum = ResultEnum.USER_BIRTH_WEIGHT_BANK;
            } else if (weight < 0 || weight > 10000) {
                resultEnum = ResultEnum.USER_BIRTH_WEIGHT_UNUSUAL;
            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            String birthDay = userRequest.getBirthDay();
            if (StringUtils.isBlank(birthDay)) {
                resultEnum = ResultEnum.USER_BIRTH_DAY_BANK;
            }
            if (resultEnum == ResultEnum.SUCCESS) {
                String currentDate = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date());
                try {
                    if (!BizUtils.checkBirth(birthDay, currentDate)) {
                        resultEnum = ResultEnum.USER_BIRTH_DAY_NEED_LESS_THAN_30DAY;
                    }
                    int between = (int) DateUtils.daysBetween(
                            DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), birthDay, DateUtils.DATA_FORMAT2);
                    if(between>0){
                        resultEnum = ResultEnum.USER_BIRTH_NOT_BORN;
                    }
                } catch (Exception e) {

                }
            }
        }


        return resultEnum;
    }

    @RequestMapping("/addStep1")
    public ModelAndView addStep1(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView();
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        UserConcurRelationQueryDO relationQueryDO = new UserConcurRelationQueryDO();
        relationQueryDO.setConcurPlanId(concurPlanId);
        relationQueryDO.setUserId(sessionUserInfo.getUserId());
        List<Integer> list = new ArrayList<Integer>();
        list.add(Status.EnsureStatus.NEW.getValue());
        relationQueryDO.setEnsureStatusList(list);
        relationQueryDO.setPageSize(100);
        List<UserConcurRelationDO> rtList = concurManager.selectByQueryPage(relationQueryDO);

        long sumMoney = 0;
        String ids = "";
        int index = 0;
        if (!CollectionUtils.isEmpty(rtList)) {
            for (UserConcurRelationDO relationDO : rtList) {
                sumMoney += relationDO.getFirstFillMoney();
                if (index == 0) {
                    ids += relationDO.getId();
                } else {
                    ids += ";" + relationDO.getId();
                }
                index++;
            }
        }
        mav.addObject("sumMoney", sumMoney);
        mav.addObject("ids", ids);
        mav.addObject("list", rtList);
        mav.addObject("concurPlan", concurPlanDO);

        /*判断跳转*/
        List<UserConcurRelationDO> userConcurRelationDOList=concurManager.getUserConcurRelation(sessionUserInfo.getUserId(),concurPlanId);
        UserDO userDO=userManager.selectByUserId(sessionUserInfo.getUserId());
        if (!CollectionUtils.isEmpty(userConcurRelationDOList) && StringUtils.isNotBlank(userDO.getMobile())) {
            mav.addObject("nextMobile","false");
        } else {
            mav.addObject("nextMobile","true");
        }
        /*判断跳转*/
        mav.setViewName("v2/concur/addStep1");
        return mav;
    }

    @RequestMapping("/firstPay")
    @ResponseBody
    public Result firstPay(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView();
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        String ids = request.getParameter("ids");
        String sumMoney = request.getParameter("sumMoney");
        PayVO payVO = concurFeePay(request, Long.parseLong(sumMoney), concurPlanId, ids);
        mav.addObject("payVO", payVO);
        mav.setViewName("v2/concur/addStep2");
        if (payVO != null && payVO.isResult()) {
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), payVO);
        } else {
            return Result.of(ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getDesc());
        }

    }

    private PayVO concurFeePay(HttpServletRequest request, long concurFee, long concurId, String ids) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        PayVO payVO = null;
        try {
            BizResult<WXPayResult> result = tradeManager.fillCash(sessionUserInfo.getUserId(), concurId, concurFee, sessionUserInfo.getOpenid(), NginxTool.getIpByNginx(request), BizFeeType.CONCURFEE.getValue(), ids);
            if (result.getModel() != null) {
                WXPayResult wxPayResult = result.getModel();
                if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode()) && WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                    String appid = wxPayResult.getAppId();
                    String nonce_str = wxPayResult.getNonceStr();
                    String signType = "MD5";
                    String packageStr = "prepay_id=" + wxPayResult.getPrepayId();
                    String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.setParameter("appId", appid);
                    requestHandler.setParameter("nonceStr", nonce_str);
                    requestHandler.setParameter("signType", signType);
                    requestHandler.setParameter("package", packageStr);
                    requestHandler.setParameter("timeStamp", timeStamp);

                    String sign = PayUtil.createWXPaySign("UTF-8", requestHandler.getParameters(), wxPayManager.getWxPaySecret(sessionUserInfo.getPfAppid()));
                    payVO = PayVO.ofSuccess();
                    payVO.setAppId(appid);
                    payVO.setConcurFee(concurFee);
                    payVO.setPaySecret(nonce_str, signType, packageStr, timeStamp, sign);
                    if (logger.isDebugEnabled()) {
                        logger.debug("member fee pay success,userId={},concurFee={}", sessionUserInfo.getUserId(), concurFee);
                    }
                } else {
                    payVO = PayVO.ofFail();
                    logger.error("member fee pay fail,userId={},concurFee={},{}", sessionUserInfo.getUserId(), concurFee, wxPayResult);
                }
            } else {

                payVO = PayVO.ofFail();
                logger.error("member fee pay fail,userId={},concurFee={}", sessionUserInfo.getUserId(), concurFee);
            }
        } catch (Exception e) {
            logger.error("member fee pay is error", e);

            payVO = PayVO.ofFail();
        }

        return payVO;
    }


    @RequestMapping("/addStep2")
    public ModelAndView addStep2(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView();
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        mav.addObject("concurPlan", concurPlanDO);
        mav.setViewName("v2/concur/addStep2");
        return mav;
    }

    @RequestMapping("/regSendCode")
    @ResponseBody
    public Result sendCode(@RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response, Model model) {
        //response.getWriter().write("dd");
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        if (StringUtils.isNotBlank(mobile)) {
            mobile = mobile.trim();
        }
        ResultEnum resultEnum = IDCard.isMobile(mobile);
        if (resultEnum == ResultEnum.SUCCESS) {
            UserDO alreadyUser = userManager.selectByMobile(mobile);
            if (alreadyUser != null && alreadyUser.getUserId() != sessionUserInfo.getUserId()) {
                resultEnum = ResultEnum.BIZ_MOBILE_ALREADY_REG_FAIL;
            }
        }
        if (resultEnum != ResultEnum.SUCCESS) {
            return Result.of(resultEnum.getCode(), resultEnum.getDesc());
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("product", "扁鹊兄弟互助");
        String code = BizUtils.generatorRandom(4);
        SmsBizDO smsBizDO = new SmsBizDO();
        smsBizDO.setRecNum(mobile);
        smsBizDO.setCode(code);
        smsBizDO.setSmsFreeSignName("注册验证");
        smsBizDO.setSmsTemplateCode("SMS_9585263");
        smsBizDO.setExtData(map);
        try {
            BizResult result = smsManager.sendSms(smsBizDO);
            if (request != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("send mobile verify success, {},{} ", smsBizDO, map);
                }
                return Result.of(result.getMsgCode(), result.getMsgInfo());
            }
        } catch (Throwable e) {
            logger.error("sendCode error", e);
        }
        return Result.of(ResultEnum.MOBILE_VERIFY_SEND_FAIL.getCode(), ResultEnum.MOBILE_VERIFY_SEND_FAIL.getDesc());

    }

    @RequestMapping("/verifySms")
    @ResponseBody
    public Result verifySms(@RequestParam("mobile") String mobile, @RequestParam("mcode") String mcode, HttpServletRequest request, HttpServletResponse response, Model model) {
        //response.getWriter().write("dd");
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        ResultEnum resultEnum = IDCard.isMobile(mobile);
        if (resultEnum == ResultEnum.SUCCESS) {
            if (StringUtils.isBlank(mcode)) {
                resultEnum = ResultEnum.CODE_BLANK_FAIL;
            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            boolean result = smsManager.verifySms(mcode, mobile, false);
            if (!result) {
                resultEnum = ResultEnum.CODE_VERIFY_FAIL;
            } else {
                userManager.perfectUser(sessionUserInfo.getUserId(), null, null, mobile);

            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            RegMobileTempDO regMobileTempDO = regMobileTempDAO.selectByMobile(mobile);
            if (regMobileTempDO == null) {
                regMobileTempDO = new RegMobileTempDO();
                regMobileTempDO.setMobile(mobile);
                regMobileTempDAO.insert(regMobileTempDO);
            }
        }
        return Result.of(resultEnum.getCode(), resultEnum.getDesc());

    }

    @RequestMapping("/addStep3")
    public ModelAndView addStep3(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView();
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        long pfAppId=concurPlanDO.getPfAppId();
        PfWeixinAuthDO pfWeixinAuthDO=pfWeixinAuthManager.selectByid(pfAppId);
        mav.addObject("attentionImgUrl",pfWeixinAuthDO.getAttentionImgUrl());
        mav.addObject("concurPlan", concurPlanDO);
        mav.setViewName("v2/concur/addStep3");
        return mav;
    }



    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
