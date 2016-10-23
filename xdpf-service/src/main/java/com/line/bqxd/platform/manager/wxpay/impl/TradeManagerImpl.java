package com.line.bqxd.platform.manager.wxpay.impl;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.*;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.ResultEnums;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.dao.UserTradeFillDAO;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayRequest;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class TradeManagerImpl implements TradeManager {
    private static Logger logger = LoggerFactory.getLogger(TradeManagerImpl.class);
    @Autowired
    private WXPayManager wxPayManager;

    @Autowired
    private UserPayManager userPayManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @Autowired
    private UserTradeFillDAO userTradeFillDAO;

    private static final String PAY_CALLBACK_URL="http://pf.xiongdihuzhu.com/v2/msg/";

    ///{appid}/payNotifyMessage

    @Override
    public BizResult<WXPayResult> fillCash(long userId, long concurId,long totalFee, String openId, String ip,String bizFeeType,String ids) {
        ConcurPlanDO concurPlanDO=concurManager.getConcurPlanById(concurId);
        if(concurPlanDO==null){
            return new BizResult(ResultEnum.SYSERROR);
        }
        long pfAppId=concurPlanDO.getPfAppId();
        UserTradeFillDO tradeDO = getTradeDO(pfAppId,concurId,userId, openId, totalFee, bizFeeType,ids);
        tradeDO.setConcurId(concurId);
        WXPayRequest payObject=null;
        if(bizFeeType.equals(BizFeeType.MEMBERFEE.getValue())){
            payObject = generateWXPayDO(pfAppId,ip, tradeDO,concurPlanDO.getName()+"会员费充值");
        }else{
            payObject = generateWXPayDO(pfAppId,ip, tradeDO,concurPlanDO.getName()+"互助金充值");
        }
        try {
            boolean result = userPayManager.insertUserTradeFillDOAndSetId(tradeDO);
            if (!result) {
                return new BizResult(ResultEnum.PAY_FILL_CASH_INSERT_DATA_RECODE_FAIL);
            } else {
                logger.warn("user fill cash insert recode success,{}", tradeDO);
            }
            WXPayResult wxPayResult = wxPayManager.wxPay(payObject,pfAppId);
            if (wxPayResult == null) {
                logger.warn("weixin pay error result=null");
            } else {
                logger.warn("weixin pay result {},{},{}", tradeDO, payObject, wxPayResult);
            }
            if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode())) {
                if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                    userPayManager.userTradeFillSuccess(tradeDO, wxPayResult);
                    return new BizResult(wxPayResult, true);
                } else {
                    //支付异常需要提示到页面
                    userPayManager.userTradeFillFail(tradeDO, wxPayResult);
                    logger.error("weixin pay error {}", wxPayResult);
                    return new BizResult(false, ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getDesc(), wxPayResult);
                }
            } else {
                //通讯异常
                userPayManager.userTradeFillFail(tradeDO, wxPayResult);
                logger.error("weixin comm error {}", wxPayResult);
                return new BizResult(false, ResultEnum.PAY_FILL_CASH_WX_COMM_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_COMM_FAIL.getDesc(), wxPayResult);
            }
        } catch (Exception e) {
            userPayManager.userTradeFillFail(tradeDO, new WXPayResult(ResultEnums.SYSERROR));
            logger.error("weixin pay error", e);
        }
        return new BizResult(ResultEnum.SYSERROR);
    }

    @Override
    public BizResult<Boolean> obtainCash(long userId, long cashFee) {
        return null;
    }

    @Override
    public BizResult<Boolean> payCash(long concurId, long userId, long payFee, UserDO subUserDO) {
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurId);
        if (concurPlanDO == null) {
            return new BizResult(ResultEnum.CONCUR_NOT_EXSIT_FAIL);
        }
        boolean payResult = userPayManager.getUserCash(userId, concurId,payFee);
        if (payResult) {
            payResult = userPayManager.insertUserTradePayDO(generateTradePayDO(concurPlanDO, userId, payFee, subUserDO));
            //失败情况下需要在做处理
            if (payResult) {
                if (logger.isDebugEnabled()) {
                    logger.debug("user pay cash success,userId={},concurId={},payFee={}", userId, concurId, payFee);
                }
            } else {
                logger.error("user pay cash insert recode fail,userId={},concurId={},payFee={}", userId, concurId, payFee);
            }

        } else {
            logger.error("user pay cash fail,userId={},concurId={},payFee={}", userId, concurId, payFee);
            return new BizResult(ResultEnum.USER_CONCUR_DEDUCT_CASH_FAIL);
        }
        return null;
    }

    @Override
    public List<UserTradeFillDO> selectTradeFillByQueryPage(UserTradeFillQueryDO userTradeFillQueryDO) {
        if (userTradeFillQueryDO == null) {
            userTradeFillQueryDO = new UserTradeFillQueryDO();
            userTradeFillQueryDO.initStartNum();
        }
        return userTradeFillDAO.selectByQuery(userTradeFillQueryDO);

    }

    @Override
    public Long countTradeFillByQueryPage(UserTradeFillQueryDO userTradeFillQueryDO) {
        if (userTradeFillQueryDO == null) {
            userTradeFillQueryDO = new UserTradeFillQueryDO();
        }
        return userTradeFillDAO.countByQuery(userTradeFillQueryDO);
    }

    private UserTradePayDO generateTradePayDO(ConcurPlanDO concurPlanDO, long userId, long payFee, UserDO subUserDO) {
        UserTradePayDO userTradePayDO = new UserTradePayDO();
        userTradePayDO.setUserId(userId);
        userTradePayDO.setFee(payFee);
        userTradePayDO.setTradeId(BizUtils.getTradeIdV1("P", BizTradeType.PAY));
        userTradePayDO.setBizType(BizType.CONCUR.getValue());
        if (subUserDO != null) {
            userTradePayDO.setSubUserId(subUserDO.getUserId());
            userTradePayDO.setSubUserName(subUserDO.getUserName());
            userTradePayDO.setExplains("支付互助金-用户" + subUserDO.getUserName());
        } else {
            userTradePayDO.setExplains("支付互助金");
        }
        //userTradePayDO.getAttach(); 附加属性看业务需求
        return userTradePayDO;
    }


    private UserTradeFillDO getTradeDO(long pfAppId,long concurId,long userId, String openId, long totalFee,String bizFeeType,String ids) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("bizFeeType", BizFeeType.of(bizFeeType).getValue());
        if (StringUtils.isNotBlank(ids)) {
            map.put("ids", ids);
        }
        map.put("userId",String.valueOf(userId));
        map.put("concurId",String.valueOf(concurId));

        UserTradeFillDO tradeDO = new UserTradeFillDO();
        tradeDO.setConcurId(concurId);
        tradeDO.setUserId(userId);
        tradeDO.setPfAppId(pfAppId);
        tradeDO.setOpenId(openId);

        //tradeDO.setAttach("支付互助金");
        //tradeDO.setId(System.currentTimeMillis() / 1000);//取当前时间为订单号
        tradeDO.setTradeId(BizUtils.getTradeIdV1("F", BizTradeType.FILL));

        tradeDO.setTradeStatus(Status.Trade.NEW.getValue());
        tradeDO.setPayStatus(Status.Pay.UNPAY.getValue());
        if(bizFeeType.equals(BizFeeType.MEMBERFEE.getValue())){
            tradeDO.setExplains("会员费充值");
        }else {
            tradeDO.setExplains("互助金充值");
        }
        //输入与支付有关的数据,扩展字段
        //tradeDO.setAttach("支付互助金");

        tradeDO.setPayType(PayType.WXPAY.getValue());
        tradeDO.setTotalFee(totalFee);
        tradeDO.setFeeType("CNY");
        tradeDO.setWxTradeType("JSAPI");

        tradeDO.setTimeStart(DateUtils.getDateTimeFormat());
        tradeDO.setAttach(JSON.toJSONString(map));
        tradeDO.setBizFeeType(bizFeeType);
        return tradeDO;
    }


    private WXPayRequest generateWXPayDO(long pfAppId,String ip, UserTradeFillDO tradeDO,String desc) {
        WXPayRequest wxPayRequest = new WXPayRequest();
        PfWeixinAuthDO weixinAuthDO=wxPayManager.getPfWeixinAuth(pfAppId);
        wxPayRequest.setAppId(weixinAuthDO.getAuthorizerAppid());
        //微信支付过程中需要APPID与MCHID匹配
        //wxPayRequest.setAppId("wx6fae0b7142e97ce8");
        wxPayRequest.setMchId(weixinAuthDO.getMchId());
        wxPayRequest.setDeviceInfo("WEB");
        wxPayRequest.setNonceStr(Sign.create_nonce_str().replaceAll("-", ""));
        wxPayRequest.setBody(desc);
        wxPayRequest.setDetail(desc);
        wxPayRequest.setAttach(tradeDO.getAttach());
        //wxPayRequest.setOutTradeNo(String.valueOf(tradeDO.getId()));
        wxPayRequest.setFeeType(tradeDO.getFeeType());
        wxPayRequest.setTotalFee(String.valueOf(tradeDO.getTotalFee()));
        wxPayRequest.setSpbillCreateIp(ip);
        //TODO 需要设置过期时间,需要业务来确定

        wxPayRequest.setTimeStart(tradeDO.getTimeStart());
        //暂时不加过期时间
        //wxPayRequest.setTimeExpire();

        wxPayRequest.setNotifyUrl(getPayCallbackUrl(weixinAuthDO.getAuthorizerAppid()));
        wxPayRequest.setTradeType(tradeDO.getWxTradeType());
        //wxPayRequest.setProductId(String.valueOf(productDO.getId()));
        wxPayRequest.setOutTradeNo(tradeDO.getTradeId());
        //需要APPID与OPENID相互匹配
        wxPayRequest.setOpenid(tradeDO.getOpenId());

        return wxPayRequest;
    }

    private String getPayCallbackUrl(String appid){
        StringBuilder sb = new StringBuilder(PAY_CALLBACK_URL);
        //{appid}/payNotifyMessage
        sb.append(appid);
        sb.append("/payNotifyMessage.htm");
        return sb.toString();
    }

}
