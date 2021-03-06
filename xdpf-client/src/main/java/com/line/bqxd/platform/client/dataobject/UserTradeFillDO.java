package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class UserTradeFillDO extends DBBaseDO implements Serializable {

    private static final long serialVersionUID = 2367902981532042155L;

    private long concurId;

    private long pfAppId;

    private String tradeId;//交易订单号

    private String openId;//用户微信ID

    private long userId;//用户ID

    private String transactionId;//第三方支付的订单号

    private String bizFeeType;//业务支付类型

    private String prepayId;//微信支付预支付交易会话标识

    /*
      参考: com.line.bqxd.platform.client.constant.PayType
     */
    private String payType;//交易类型,微信支付,支付宝支付

    private int tradeStatus;//交易状态

    private int payStatus;//付款状态

    //private long concurPlanId;//互助计划产品ID

    //private String concurPlanType;

    private long totalFee; //订单总费用

    private String feeType; //货币类型

    private String  bankType;//银行类型

    private long cashFee;//现金支付金额订单现金支付金额

    private String cashFeeType;//现金货币类型

    private String timeStart; //交易开始时间

    private String timeExpire; //交易结束时间

    private String timeEnd;//支付完成时间

    /*
      参考:com.line.bqxd.platform.client.constant.WXTradeTpey
     */
    private String wxTradeType; //微信交易类型  JSAPI，NATIVE，APP

    private String attach;//附加数据，该字段主要用于商户携带订单的自定义数据

    private String explains;  //订单说明

    private String errCode;

    private String errCodeDes;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public long getCashFee() {
        return cashFee;
    }

    public void setCashFee(long cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
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

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getWxTradeType() {
        return wxTradeType;
    }

    public void setWxTradeType(String wxTradeType) {
        this.wxTradeType = wxTradeType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }


    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }


    public String getBizFeeType() {
        return bizFeeType;
    }

    public void setBizFeeType(String bizFeeType) {
        this.bizFeeType = bizFeeType;
    }

    public long getConcurId() {
        return concurId;
    }

    public void setConcurId(long concurId) {
        this.concurId = concurId;
    }

    public long getPfAppId() {
        return pfAppId;
    }

    public void setPfAppId(long pfAppId) {
        this.pfAppId = pfAppId;
    }


}
