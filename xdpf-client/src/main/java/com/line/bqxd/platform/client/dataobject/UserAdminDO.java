package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/5/2.
 */
public class UserAdminDO extends DBBaseDO implements Serializable {

    private static final long serialVersionUID = 3054474041968717204L;
    private String loginName;

    private String password;

    private String showName;

    private String lastLoginTime;
    private String lastLoginIp;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
