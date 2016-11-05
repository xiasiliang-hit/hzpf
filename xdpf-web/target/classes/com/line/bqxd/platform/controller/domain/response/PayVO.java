package com.line.bqxd.platform.controller.domain.response;

import com.line.bqxd.platform.client.common.Base;

/**
 * Created by huangjianfei on 16/9/17.
 */
public class PayVO extends Base {
    private String appId;

    private String nonceStr;

    private String signType;

    private String packages;

    private String timeStamp;

    private String sign;

    private long concurFee;

    private boolean result;

	private String msg;

	public void addMsg(String s)
	{
		msg+= s;
	}

	public String getMsg()
	{
		return msg;
	}
	
    public PayVO(boolean result) {
        this.result = result;
    }

    public void setPaySecret(String nonceStr, String signType, String packages, String timeStamp, String sign) {
        this.nonceStr = nonceStr;
        this.signType = signType;
        this.packages = packages;
        this.timeStamp = timeStamp;
        this.sign = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getConcurFee() {
        return concurFee;
    }

    public void setConcurFee(long concurFee) {
        this.concurFee = concurFee;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public static PayVO ofSuccess(){
        return new PayVO(true);
    }

    public static PayVO ofFail(){
        return new PayVO(false);
    }
}
