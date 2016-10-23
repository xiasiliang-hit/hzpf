package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/5/2.
 */
public class ConcurPlanDO extends DBBaseDO implements Serializable {

    private static final long serialVersionUID = 933393205240637806L;

    private long pfAppId;

    private String name;

    private String ensureName;

    private long ensureMoney;

    private String ensureMoneyDesc;

    private String ensureTime;

    private String ensureRequire;

    private String firstStoreMoneyDesc;

    private int firstStoreMoney;

    private String costMoneyDesc;

    private int costMoney;

    private String ensureContinueDesc;

    private int ensureContinue;

    private String bannerUrl;

    private String tenetUrl;

    private String concurDescUrl;

    private String serviceUrl;

    private String detailDesc;

    public long getPfAppId() {
        return pfAppId;
    }

    public void setPfAppId(long pfAppId) {
        this.pfAppId = pfAppId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnsureName() {
        return ensureName;
    }

    public void setEnsureName(String ensureName) {
        this.ensureName = ensureName;
    }

    public long getEnsureMoney() {
        return ensureMoney;
    }

    public void setEnsureMoney(long ensureMoney) {
        this.ensureMoney = ensureMoney;
    }

    public String getEnsureTime() {
        return ensureTime;
    }

    public void setEnsureTime(String ensureTime) {
        this.ensureTime = ensureTime;
    }

    public String getEnsureRequire() {
        return ensureRequire;
    }

    public void setEnsureRequire(String ensureRequire) {
        this.ensureRequire = ensureRequire;
    }

    public String getFirstStoreMoneyDesc() {
        return firstStoreMoneyDesc;
    }

    public void setFirstStoreMoneyDesc(String firstStoreMoneyDesc) {
        this.firstStoreMoneyDesc = firstStoreMoneyDesc;
    }

    public int getFirstStoreMoney() {
        return firstStoreMoney;
    }

    public void setFirstStoreMoney(int firstStoreMoney) {
        this.firstStoreMoney = firstStoreMoney;
    }

    public String getCostMoneyDesc() {
        return costMoneyDesc;
    }

    public void setCostMoneyDesc(String costMoneyDesc) {
        this.costMoneyDesc = costMoneyDesc;
    }

    public int getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(int costMoney) {
        this.costMoney = costMoney;
    }

    public String getEnsureContinueDesc() {
        return ensureContinueDesc;
    }

    public void setEnsureContinueDesc(String ensureContinueDesc) {
        this.ensureContinueDesc = ensureContinueDesc;
    }

    public int getEnsureContinue() {
        return ensureContinue;
    }

    public void setEnsureContinue(int ensureContinue) {
        this.ensureContinue = ensureContinue;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getTenetUrl() {
        return tenetUrl;
    }

    public void setTenetUrl(String tenetUrl) {
        this.tenetUrl = tenetUrl;
    }

    public String getConcurDescUrl() {
        return concurDescUrl;
    }

    public void setConcurDescUrl(String concurDescUrl) {
        this.concurDescUrl = concurDescUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getEnsureMoneyDesc() {
        return ensureMoneyDesc;
    }

    public void setEnsureMoneyDesc(String ensureMoneyDesc) {
        this.ensureMoneyDesc = ensureMoneyDesc;
    }
}
