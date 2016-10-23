package com.line.bqxd.platform.manager.sms;

import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/6.
 * 发送短信对象
 */
public class SmsBizDO extends Base implements Serializable{

    private static final long serialVersionUID = -1771002353025790186L;

    /**
     * 接收短信手机号码
     */
    private String recNum;

    /**
     * 短信模板ID
     */
    private String smsTemplateCode;

    /**
     * 验证码
     */
    private String code;

    /**
     * 短信签名
     */
    private String smsFreeSignName;

    private Map<String,String> extData=null;


    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSmsFreeSignName() {
        return smsFreeSignName;
    }

    public void setSmsFreeSignName(String smsFreeSignName) {
        this.smsFreeSignName = smsFreeSignName;
    }

    public Map<String, String> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, String> extData) {
        this.extData = extData;
    }
}
