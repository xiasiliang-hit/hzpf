package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/10.
 */
public class ConcurPlanQueryDO extends DBBaseQueryDO {

    private long pfAppId;


    public long getPfAppId() {
        return pfAppId;
    }

    public void setPfAppId(long pfAppId) {
        this.pfAppId = pfAppId;
    }
}
