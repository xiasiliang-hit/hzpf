package com.line.bqxd.platform.manager.wxpay.dataobject;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/7/13.
 */
public class WXOrderResult extends WXPayBaseResult implements Serializable{
    private static final long serialVersionUID = -8693474983984532631L;

    /**
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */

   // device_info openid is_subscribe trade_type trade_state bank_type total_fee settlement_total_fee fee_type cash_fee cash_fee_type
    //transaction_id out_trade_no attach  time_end  trade_state_desc

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("trade_state")
    private String tradeState;

    @XStreamAlias("bank_type")
    private String bankType;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("settlement_total_fee")
    private String settlementTotalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("time_end")
    private String timeEnd;

    @XStreamAlias("trade_state_desc")
    private String tradeStateDesc;

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(String settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

}
