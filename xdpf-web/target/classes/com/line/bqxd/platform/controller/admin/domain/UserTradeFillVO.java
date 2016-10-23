package com.line.bqxd.platform.controller.admin.domain;

import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;

/**
 * Created by huangjianfei on 16/8/3.
 */
public class UserTradeFillVO extends UserTradeFillDO {
    private String userName;

    private String nickName;

    private String headImgUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
