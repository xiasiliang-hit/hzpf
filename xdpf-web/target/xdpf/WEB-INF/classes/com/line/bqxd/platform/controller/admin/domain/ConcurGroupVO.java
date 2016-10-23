package com.line.bqxd.platform.controller.admin.domain;

/**
 * Created by huangjianfei on 16/5/24.
 */
public class ConcurGroupVO {
    private String userName;

    private String nickName;

    private long  quantity;

    private long  subUserQuantity;

    private String userHeadImgUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHeadImgUrl() {
        return userHeadImgUrl;
    }

    public void setUserHeadImgUrl(String userHeadImgUrl) {
        this.userHeadImgUrl = userHeadImgUrl;
    }

    public long getSubUserQuantity() {
        return subUserQuantity;
    }

    public void setSubUserQuantity(long subUserQuantity) {
        this.subUserQuantity = subUserQuantity;
    }
}
