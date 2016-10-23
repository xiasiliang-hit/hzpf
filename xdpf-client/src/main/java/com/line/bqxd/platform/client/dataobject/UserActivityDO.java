package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;

import java.util.Date;

/**
 * Created by huangjianfei on 16/7/29.
 */
public class UserActivityDO extends DBBaseDO {

    private static final long serialVersionUID = 6933175169883927865L;
    private String name;

    private String descr;

    private long fee;

    private Date beginTime;

    private Date endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
