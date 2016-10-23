package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

/**
 * Created by huangjianfei on 16/7/27.
 */
public class PfCorpDO extends DBBaseDO {
    private static final long serialVersionUID = -1889818665791480862L;
    private String corpName;

    private String corpLogo;

    private String contactName;

    private String tel;

    private String mobile;

    private long staffSum;

    private int payType;

    private String payMchId;

    private String payMchSecret;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpLogo() {
        return corpLogo;
    }

    public void setCorpLogo(String corpLogo) {
        this.corpLogo = corpLogo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getStaffSum() {
        return staffSum;
    }

    public void setStaffSum(long staffSum) {
        this.staffSum = staffSum;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayMchId() {
        return payMchId;
    }

    public void setPayMchId(String payMchId) {
        this.payMchId = payMchId;
    }

    public String getPayMchSecret() {
        return payMchSecret;
    }

    public void setPayMchSecret(String payMchSecret) {
        this.payMchSecret = payMchSecret;
    }
}
