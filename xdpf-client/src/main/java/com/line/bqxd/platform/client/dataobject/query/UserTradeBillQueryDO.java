package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class UserTradeBillQueryDO extends UserTradeQueryDO {

    private String listType;

    private long concurId;

    private long userId;

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public long getConcurId() {
        return concurId;
    }

    public void setConcurId(long concurId) {
        this.concurId = concurId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
