package com.line.bqxd.platform.manager.wxpay.dataobject;

import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class WXPayRequest extends Base implements Serializable {
    /**
     * 此属性参考微信支付文档,文档地址:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     */
    private static final long serialVersionUID = 139130021304921941L;
    /**
     * 微信分配的公众账号ID
     */
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    private String mchId;

    /**
     * 支付秘钥 微信商户平台设置的KEY值
     */
    private String paySecret;

    /**
     * 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     */
    private String deviceInfo;
    /**
     * 随机字符串，不长于32位
     */
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商品或支付单简要描述
     */
    private String body;

    /**
     * 商品或支付单简要描述
     */
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;

    /**
     * 商户系统内部的订单号,32个字符内、可包含字母,需要做唯一
     */
    private String outTradeNo;

    /**
     *符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String feeType;

    /**
     *订单总金额，单位为分
     */
    private String totalFee;

    /**
     *APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    private String spbillCreateIp;

    /**
     *订单生成时间，格式为yyyyMMddHHmmss
     */
    private String timeStart;

    /**
     *订单失效时间，格式为yyyyMMddHHmmss
     */
    private String timeExpire;

    /**
     *商品标记，代金券或立减优惠功能的参数
     */
    private String goodsTag;

    /**
     *接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
     */
    private String notifyUrl;

    /**
     *取值如下：JSAPI，NATIVE，APP
     */
    private String tradeType;

    /**
     *trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义
     */
    private String productId;

    /**
     *no_credit--指定不能使用信用卡支付
     */
    private String limitPay;

    /**
     *trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
     */
    private String openid;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPaySecret() {
        return paySecret;
    }

    public void setPaySecret(String paySecret) {
        this.paySecret = paySecret;
    }
}
