package com.line.bqxd.platform.client.dataobject.query;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class CleanExtendFeeQueryDO extends DBBaseQueryDO {

    private long extendId;

    private int status;

    private String clearDate;


    public long getExtendId() {
        return extendId;
    }

    public void setExtendId(long extendId) {
        this.extendId = extendId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }
}
