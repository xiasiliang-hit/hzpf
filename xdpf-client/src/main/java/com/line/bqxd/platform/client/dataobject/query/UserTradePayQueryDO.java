package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class UserTradePayQueryDO extends UserTradeQueryDO {
    private String subUserName=null;

    public String getSubUserName() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName = subUserName;
    }
}
