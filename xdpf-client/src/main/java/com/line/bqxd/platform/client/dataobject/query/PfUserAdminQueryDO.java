package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class PfUserAdminQueryDO extends DBBaseQueryDO {

    private String loginName;

    private String nickName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
