package com.line.bqxd.platform.manager.wxpay;

import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXOrderResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayRequest;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;

/**
 * Created by huangjianfei on 16/4/28.
 */
public interface WXPayManager {

    /**
     * 执行微信支付统一下单
     * @param wxPayRequest
     * @return
     */
    public WXPayResult wxPay(WXPayRequest wxPayRequest,long pfAppId);

    public String getMchId(long pfAppId);

    public String getWxPaySecret(long pfAppId);
    public PfWeixinAuthDO getPfWeixinAuth(long pfAppId);

    public String getpayCallbackUrl();

    /**
     * 根据交易号查询微信订单
     * @param tradeId
     * @return
     */
    public WXOrderResult queryOrderByTradeId(String tradeId,long pfAppId);

    /**
     * 根据微信支付订单号查询订单
     * @param tradeId
     * @return
     */
    public WXOrderResult queryOrderByTransactionId(String tradeId,long pfAppId);

    /**
     * 根据日期处理异常订单
     * @param day
     */
    public void handleUnusualTrade(String day);
}
