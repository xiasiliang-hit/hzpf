package com.line.bqxd.platform.controller.admin.domain;

import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;

/**
 * Created by huangjianfei on 16/5/26.
 */
public class UserConcurRelationVO extends UserConcurRelationDO {

    private String ageDesc;

    private String ensureDesc;

    private String  upperLimitYuan;

    private String  ratioFormat;

    private long realDunkMoney;

    private boolean canApply;

    public String getAgeDesc() {
        return ageDesc;
    }

    public void setAgeDesc(String ageDesc) {
        this.ageDesc = ageDesc;
    }

    public String getEnsureDesc() {
        return ensureDesc;
    }

    public void setEnsureDesc(String ensureDesc) {
        this.ensureDesc = ensureDesc;
    }


    public String getUpperLimitYuan() {
        return upperLimitYuan;
    }

    public void setUpperLimitYuan(String upperLimitYuan) {
        this.upperLimitYuan = upperLimitYuan;
    }


    public String getRatioFormat() {
        return ratioFormat;
    }

    public void setRatioFormat(String ratioFormat) {
        this.ratioFormat = ratioFormat;
    }

    public long getRealDunkMoney() {
        return realDunkMoney;
    }

    public void setRealDunkMoney(long realDunkMoney) {
        this.realDunkMoney = realDunkMoney;
    }

    public boolean isCanApply() {
        return canApply;
    }

    public void setCanApply(boolean canApply) {
        this.canApply = canApply;
    }
}
