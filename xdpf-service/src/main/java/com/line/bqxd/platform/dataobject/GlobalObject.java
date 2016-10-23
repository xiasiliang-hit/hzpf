package com.line.bqxd.platform.dataobject;

import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/21.
 */
public class GlobalObject extends Base implements Serializable{
    private static final long serialVersionUID = -7080490595536234491L;
    private String accessToken = "";

    private String refreshToken;
    /**
     * 微信JS 调用的jssdk_ticket 令牌
     */
    private String ticket = "";

    private int expiresIn = 0;
    /**
     * JS 页面是否需要进行调试功能,线上环境默认就是false
     */
    private boolean debugLevel = false;

    /**
     * 微信平台公众号ID
     */
    private String appId;
    /**
     * 平台自己的ID
     */
    private long pfAppId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(boolean debugLevel) {
        this.debugLevel = debugLevel;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getPfAppId() {
        return pfAppId;
    }

    public void setPfAppId(long pfAppId) {
        this.pfAppId = pfAppId;
    }
}
