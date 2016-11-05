package com.line.bqxd.platform.controller.vo;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.common.security.Sign;

/**
 * Created by huangjianfei on 16/4/25.
 */
public class SignVO extends Base{

    public SignVO(){

    }
    public SignVO(String timestamp,String nonceStr,String signature,String wxAppId){
        this.timestamp=timestamp;
        this.nonceStr=nonceStr;
        this.signature=signature;
        this.wxAppId=wxAppId;
    }
    private boolean debugLevel;
    private String timestamp;
    private String nonceStr;
    private String signature;
    private String wxAppId;


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }


    public boolean isDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(boolean debugLevel) {
        this.debugLevel = debugLevel;
    }

}
