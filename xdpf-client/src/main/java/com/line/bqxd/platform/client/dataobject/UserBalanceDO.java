package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

/**
 * Created by huangjianfei on 16/5/17.
 */
public class UserBalanceDO extends DBBaseDO{

    private long userId;

    private long pfAppId;

    private long concurId;

    private long balance;

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

    public long getConcurId() {
        return concurId;
    }

    public void setConcurId(long concurId) {
        this.concurId = concurId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
