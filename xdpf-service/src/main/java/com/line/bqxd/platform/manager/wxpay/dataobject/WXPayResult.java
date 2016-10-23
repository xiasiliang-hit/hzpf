package com.line.bqxd.platform.manager.wxpay.dataobject;

import com.line.bqxd.platform.common.ResultEnums;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class WXPayResult extends WXPayBaseResult implements Serializable{


    private static final long serialVersionUID = 6702443561553262259L;
    /**
     * 以下字段在return_code为SUCCESS的时候有返回
     */

    @XStreamAlias("device_info")
    private String deviceInfo;


    /**
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("prepay_id")
    private String prepayId;

    @XStreamAlias("code_url")
    private String codeUrl;

    public WXPayResult() {
        super();
    }

    public WXPayResult(String returnCode, String returnMsg) {
        super(returnCode, returnMsg);
    }

    public WXPayResult(ResultEnums resultEnum) {
        super(resultEnum);
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
