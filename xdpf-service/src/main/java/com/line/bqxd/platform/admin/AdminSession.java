package com.line.bqxd.platform.admin;

import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/5/21.
 */
public class AdminSession extends Base implements Serializable {
    private String userName;
    private long corpId;

    private String nickName;

    private long userId;

    public AdminSession(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCorpId() {
        return corpId;
    }

    public void setCorpId(long corpId) {
        this.corpId = corpId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
