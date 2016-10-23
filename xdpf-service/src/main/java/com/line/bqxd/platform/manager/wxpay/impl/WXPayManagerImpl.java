package com.line.bqxd.platform.manager.wxpay.impl;

import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.HttpResult;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.message.SerializeXmlUtil;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wxpay.RequestHandler;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXBaseDO;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXOrderResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayRequest;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class WXPayManagerImpl implements WXPayManager {
    private static Logger logger = LoggerFactory.getLogger(WXPayManagerImpl.class);


    private String payCallbackUrl;

    private static final String WEIXIN_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static final String WEIXIN_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "WXPayCollateThread");
                }
            });

    private static final String INIT_RUN_HOUR = "02";

    private static final long DAY_HAVE_MILLIS = 24 * 60 * 60 * 1000;


    @Autowired
    private UserPayManager userPayManager;

    private String configInitDelay = "";

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @Override
    public WXPayResult wxPay(WXPayRequest wxPayRequest,long pfAppId) {
        RequestHandler requestHandler = createRequestHandle(wxPayRequest);
        String urlPostData = null;
        try {
            urlPostData = requestHandler.getRequestURL(getWxPaySecret(pfAppId), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode error", e);
        }
        if (StringUtils.isBlank(urlPostData)) {
            logger.warn("urlPost data null");
            return null;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("WXPayManager wxPay postData={}", urlPostData);
            }
        }
        //urlPostData="<xml><appid>wx6fae0b7142e97ce8</appid><attach>test</attach><body>兄弟互助测试版</body><detail>详细的兄弟互助测试版</detail><device_info>WEB</device_info><fee_type>CNY</fee_type><mch_id>1260519601</mch_id><nonce_str>1095cb950d0042e6b8ce2352c4c0abfa</nonce_str><notify_url>http://139.129.58.215/wxpay/callback/notifyMessage.htm</notify_url><openid>ot3tXs0AZNShis5r4mG6muF-5hEc</openid><out_trade_no>1461854485</out_trade_no><product_id>1</product_id><spbill_create_ip>139.129.58.215</spbill_create_ip><total_fee>1</total_fee><trade_type>JSAPI</trade_type><sign>CF9470CD29EC4DDD67433CFEA1A2EB38</sign></xml>";
        try {
            HttpResult httpResult = HttpUtil.sendOriginally(WEIXIN_PAY_URL, urlPostData, "UTF-8");
            if (httpResult != null && httpResult.isSuccess()) {
                XStream xs = SerializeXmlUtil.createXstream();
                xs.processAnnotations(WXPayResult.class);
                // 将指定节点下的xml节点数据映射为对象
                xs.alias("xml", WXPayResult.class);
                WXPayResult wxPayResult = (WXPayResult) xs.fromXML(httpResult.getContent());
                return wxPayResult;
            } else {
                logger.error("weixin pay error,code={},content={}", httpResult.getStatusCode(), httpResult.getContent());
            }
        } catch (Exception e) {
            logger.error("weixin pay error", e);
        }
        return null;
    }

    private RequestHandler createRequestHandle(WXPayRequest wxPayRequest) {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.setParameter("appid", wxPayRequest.getAppId());
        requestHandler.setParameter("mch_id", wxPayRequest.getMchId());
        requestHandler.setParameter("device_info", wxPayRequest.getDeviceInfo());
        requestHandler.setParameter("nonce_str", wxPayRequest.getNonceStr());
        requestHandler.setParameter("body", wxPayRequest.getBody());
        requestHandler.setParameter("detail", wxPayRequest.getDetail());
        requestHandler.setParameter("attach", wxPayRequest.getAttach());
        requestHandler.setParameter("out_trade_no", wxPayRequest.getOutTradeNo());
        requestHandler.setParameter("fee_type", wxPayRequest.getFeeType());
        requestHandler.setParameter("total_fee", wxPayRequest.getTotalFee());
        requestHandler.setParameter("spbill_create_ip", wxPayRequest.getSpbillCreateIp());
        if (StringUtils.isNotBlank(wxPayRequest.getTimeStart())) {
            requestHandler.setParameter("time_start", wxPayRequest.getTimeStart());
        }
        if (StringUtils.isNotBlank(wxPayRequest.getTimeExpire())) {
            requestHandler.setParameter("time_expire", wxPayRequest.getTimeExpire());
        }
        requestHandler.setParameter("notify_url", wxPayRequest.getNotifyUrl());
        requestHandler.setParameter("trade_type", wxPayRequest.getTradeType());
        requestHandler.setParameter("product_id", wxPayRequest.getProductId());
        requestHandler.setParameter("openid", wxPayRequest.getOpenid());
        return requestHandler;
    }


    @Override
    public String getMchId(long pfAppId) {
        PfWeixinAuthDO pfWeixinAuthDO=getPfWeixinAuth(pfAppId);
        if(pfWeixinAuthDO==null){
            return null;
        }
        return pfWeixinAuthDO.getMchId();
    }

    @Override
    public String getWxPaySecret(long pfAppId) {
        PfWeixinAuthDO pfWeixinAuthDO=getPfWeixinAuth(pfAppId);
        if(pfWeixinAuthDO==null){
            return null;
        }
        return pfWeixinAuthDO.getMchSecret();
    }

    @Override
    public PfWeixinAuthDO getPfWeixinAuth(long pfAppId) {
        if(pfAppId<=0){
            return null;
        }
        return pfWeixinAuthManager.selectByid(pfAppId);
    }

    @Override
    public String getpayCallbackUrl() {
        return null;
    }

    @Override
    public WXOrderResult queryOrderByTradeId(String tradeId,long pfAppId) {
        return queryOrder(tradeId, pfAppId, true);
    }

    private WXOrderResult queryOrder(String queryId,long pfAppId, boolean isTradeId) {
        RequestHandler requestHandler = new RequestHandler();
        PfWeixinAuthDO weixinAuth=getPfWeixinAuth(pfAppId);
        requestHandler.setParameter("appid", weixinAuth.getAuthorizerAppid());

        //requestHandler.setParameter("appid", application.getAppId());
        requestHandler.setParameter("mch_id", weixinAuth.getMchId());
        requestHandler.setParameter("device_info", "WEB");
        requestHandler.setParameter("nonce_str", Sign.create_nonce_str().replaceAll("-", ""));
        if (isTradeId) {
            requestHandler.setParameter("out_trade_no", queryId);
        } else {
            requestHandler.setParameter("transaction_id", queryId);
        }
        String urlPostData = null;
        try {
            urlPostData = requestHandler.getRequestURL(getWxPaySecret(pfAppId), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode error", e);
        }
        if (StringUtils.isBlank(urlPostData)) {
            logger.warn("urlPost data null");
            return null;
        }
        try {
            HttpResult httpResult = HttpUtil.sendOriginally(WEIXIN_ORDER_QUERY_URL, urlPostData, "UTF-8");
            if (httpResult != null && httpResult.isSuccess()) {
                XStream xs = SerializeXmlUtil.createXstream();
                xs.processAnnotations(WXOrderResult.class);
                // 将指定节点下的xml节点数据映射为对象
                xs.alias("xml", WXOrderResult.class);
                WXOrderResult wxOrderResult = (WXOrderResult) xs.fromXML(httpResult.getContent());
                return wxOrderResult;
            } else {
                logger.error("queryOrderByTradeIderror,code={},content={}", httpResult.getStatusCode(), httpResult.getContent());
            }
        } catch (Exception e) {
            logger.error("queryOrderByTradeId error", e);
        }
        return null;
    }

    @Override
    public WXOrderResult queryOrderByTransactionId(String tradeId,long pfAppId) {
        return queryOrder(tradeId,pfAppId, false);
    }

    @Override
    public void handleUnusualTrade(String day) {
        try {
            List<UserTradeFillDO> list = userPayManager.selectUnusualTradeFillByDay(day);
            if (!CollectionUtils.isEmpty(list)) {
                for (UserTradeFillDO userTradeFillDO : list) {
                    collate(userTradeFillDO);
                }
            } else {
                logger.warn("execute unusual trade null,day={}", day);
            }
        } catch (Exception e) {
            logger.error("execute unusual trade error", e);
        }
    }


    public synchronized void init() throws Exception {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Date currentDate=new Date();
                Calendar calendar=Calendar.getInstance();
                logger.info("execute unusual trade thread start,date={}",DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT1, calendar.getTime()));
                calendar.add(Calendar.DAY_OF_YEAR,-1);
                String day = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, calendar.getTime());
                handleUnusualTrade(day);
                logger.info("execute unusual trade thread end,date={}",DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT1, new Date()));
            }
        }, calculateInitDelay(this.configInitDelay), DAY_HAVE_MILLIS, TimeUnit.MILLISECONDS);
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }


    private void collate(UserTradeFillDO userTradeFillDO) {
        WXOrderResult wxOrderResult = queryOrderByTradeId(userTradeFillDO.getTradeId(),userTradeFillDO.getPfAppId());
        if (wxOrderResult == null) {
            logger.warn("collate get wxpay null,{}", userTradeFillDO);
            return;
        }
        if (WXBaseDO.WX_SUCCESS_CODE.equals(wxOrderResult.getReturnCode()) && WXBaseDO.WX_SUCCESS_CODE.equals(wxOrderResult.getResultCode())) {
            if (wxOrderResult.getTradeState().equalsIgnoreCase(Status.WXPay.SUCCESS.name())) {
                userPayManager.userTradeFillCollate(wxOrderResult);
            } else {
                if (!wxOrderResult.getTradeState().equalsIgnoreCase(Status.WXPay.USERPAYING.name())) {
                    logger.warn("collate execute fail,userId={},tradeId={}", userTradeFillDO.getUserId(), userTradeFillDO.getTradeId());
                    userPayManager.userTradeFillFail(userTradeFillDO, wxOrderResult);
                } else {
                    logger.warn("collate execute fail,USERPAYING,{}", wxOrderResult);
                }
            }

        } else {
            logger.warn("collate execute fail,userId={},tradeId={}", userTradeFillDO.getUserId(), userTradeFillDO.getTradeId());
            userPayManager.userTradeFillFail(userTradeFillDO, wxOrderResult);
        }
    }

    public static long calculateInitDelay(String configInitDelay) throws Exception {
        if (StringUtils.isNotBlank(configInitDelay)) {
            long second = Long.parseLong(configInitDelay);
            if (second > 0) {
                return Long.parseLong(configInitDelay) * 1000;
            }
        }
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String dateStr = null;
        if (hour >= Integer.parseInt(INIT_RUN_HOUR)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dateStr = DateUtils.getDateTimeFormat(calendar.getTimeInMillis()).substring(0, 8);
        } else {
            dateStr = DateUtils.getDateTimeFormat(calendar.getTimeInMillis()).substring(0, 8);
        }
        dateStr += INIT_RUN_HOUR + "0001";
        long endTime = DateUtils.parse(dateStr, DateUtils.DATA_TIME_FORMAT2).getTime();
        return endTime - currentTime;
    }

    public void setConfigInitDelay(String configInitDelay) {
        this.configInitDelay = configInitDelay;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(calculateInitDelay(null));
    }
}
