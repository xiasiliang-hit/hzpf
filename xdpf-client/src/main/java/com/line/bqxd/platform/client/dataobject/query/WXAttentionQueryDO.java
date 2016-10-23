package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class WXAttentionQueryDO extends DBBaseQueryDO {

    private String openId;

    private String tag;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
