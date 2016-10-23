package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class UserAppOpenIdQueryDO extends DBBaseQueryDO {
    private long userId;

    private long pfAppId;

    private String unionId;

    private String openId;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPfAppId() {
        return pfAppId;
    }

    public void setPfAppId(long pfAppId) {
        this.pfAppId = pfAppId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
