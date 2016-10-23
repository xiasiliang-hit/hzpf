package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/5/2.
 */
public class ClaimApplyDO extends DBBaseDO implements Serializable{

    private static final long serialVersionUID = 1657552234394417045L;
    private long concurPlanId;

    private long userConcurRelationId;

    private long userId;

    private String userName ;

    private String verifyTime;

    private String verifyUser;

    private String verifyUserMsg;

    private String verifyTel;

    private String verifyHospital;

    private long  verifyEventId;

    private int  verifyUserResult;

    private int verifyResult;

    private String picArray;

    private String descr;

    private String followUserName;

    private String followUserTel;

    private int  status;

    private String result;

    private String attach;

    private long preCollectMoney;

    private long collectMoney;

    private String publishStartDay;

    private String dunkDay;

    private String bandyStartDay;

    private String eventEndDay;

    private String eventSubmitDay;

    private int monthIndex;

    private long joinUserCount;

    private long preJoinUserCount;

    private String bankProofPic;

    public long getConcurPlanId() {
        return concurPlanId;
    }

    public void setConcurPlanId(long concurPlanId) {
        this.concurPlanId = concurPlanId;
    }

    public long getUserConcurRelationId() {
        return userConcurRelationId;
    }

    public void setUserConcurRelationId(long userConcurRelationId) {
        this.userConcurRelationId = userConcurRelationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyUser() {
        return verifyUser;
    }
    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser;
    }

    public String getVerifyTel() {
        return verifyTel;
    }

    public void setVerifyTel(String verifyTel) {
        this.verifyTel = verifyTel;
    }

    public String getVerifyHospital() {
        return verifyHospital;
    }

    public void setVerifyHospital(String verifyHospital) {
        this.verifyHospital = verifyHospital;
    }

    public long getVerifyEventId() {
        return verifyEventId;
    }

    public void setVerifyEventId(long verifyEventId) {
        this.verifyEventId = verifyEventId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getFollowUserName() {
        return followUserName;
    }

    public void setFollowUserName(String followUserName) {
        this.followUserName = followUserName;
    }

    public String getFollowUserTel() {
        return followUserTel;
    }

    public void setFollowUserTel(String followUserTel) {
        this.followUserTel = followUserTel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicArray() {
        return picArray;
    }

    public void setPicArray(String picArray) {
        this.picArray = picArray;
    }

    public long getPreCollectMoney() {
        return preCollectMoney;
    }

    public void setPreCollectMoney(long preCollectMoney) {
        this.preCollectMoney = preCollectMoney;
    }

    public long getCollectMoney() {
        return collectMoney;
    }

    public void setCollectMoney(long collectMoney) {
        this.collectMoney = collectMoney;
    }

    public String getPublishStartDay() {
        return publishStartDay;
    }

    public void setPublishStartDay(String publishStartDay) {
        this.publishStartDay = publishStartDay;
    }

    public String getBandyStartDay() {
        return bandyStartDay;
    }

    public void setBandyStartDay(String bandyStartDay) {
        this.bandyStartDay = bandyStartDay;
    }

    public String getEventEndDay() {
        return eventEndDay;
    }

    public void setEventEndDay(String eventEndDay) {
        this.eventEndDay = eventEndDay;
    }

    public String getEventSubmitDay() {
        return eventSubmitDay;
    }

    public void setEventSubmitDay(String eventSubmitDay) {
        this.eventSubmitDay = eventSubmitDay;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public long getJoinUserCount() {
        return joinUserCount;
    }

    public void setJoinUserCount(long joinUserCount) {
        this.joinUserCount = joinUserCount;
    }

    public String getBankProofPic() {
        return bankProofPic;
    }

    public void setBankProofPic(String bankProofPic) {
        this.bankProofPic = bankProofPic;
    }

    public String getVerifyUserMsg() {
        return verifyUserMsg;
    }

    public void setVerifyUserMsg(String verifyUserMsg) {
        this.verifyUserMsg = verifyUserMsg;
    }

    public int getVerifyUserResult() {
        return verifyUserResult;
    }

    public void setVerifyUserResult(int verifyUserResult) {
        this.verifyUserResult = verifyUserResult;
    }

    public int getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(int verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getDunkDay() {
        return dunkDay;
    }

    public void setDunkDay(String dunkDay) {
        this.dunkDay = dunkDay;
    }

    public long getPreJoinUserCount() {
        return preJoinUserCount;
    }

    public void setPreJoinUserCount(long preJoinUserCount) {
        this.preJoinUserCount = preJoinUserCount;
    }
}
