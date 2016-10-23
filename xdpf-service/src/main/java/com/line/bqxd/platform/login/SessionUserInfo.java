package com.line.bqxd.platform.login;


import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/27.‰
 */
public class SessionUserInfo extends Base implements Serializable {

    private static final long serialVersionUID = -6295166060349091229L;
    private long pfAppid;

    private String appid;

    private long userId;

    private String openid;

    private String unionid;

    private String nickName;

    private String userName;

    private Object extData;

    private String token;

    public long getUserId() {
        return userId;

    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public long getPfAppid() {
        return pfAppid;
    }

    public void setPfAppid(long pfAppid) {
        this.pfAppid = pfAppid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
