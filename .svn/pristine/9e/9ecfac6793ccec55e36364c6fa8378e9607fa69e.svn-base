package com.line.bqxd.platform.manager.wxpay.dataobject;

import com.line.bqxd.platform.common.ResultEnums;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class WXPayBaseResult extends WXBaseDO implements Serializable{


    private static final long serialVersionUID = -1741766496168684069L;
    /**
     * 以下字段在return_code为SUCCESS的时候有返回
     */
    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("mch_id")
    private String mchId;


    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;



    public WXPayBaseResult() {
        super();
    }

    public WXPayBaseResult(String returnCode, String returnMsg) {
        super(returnCode, returnMsg);
    }

    public WXPayBaseResult(ResultEnums resultEnum) {
        super(resultEnum);
    }

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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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


}
