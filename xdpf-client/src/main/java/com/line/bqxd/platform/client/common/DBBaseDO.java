package com.line.bqxd.platform.client.common;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by luohai.hjf on 15-6-4.
 */
public abstract class DBBaseDO extends Base implements Serializable {

    private static final long serialVersionUID = 5074666949273727712L;
    protected long id;

   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected  void clear(){}

    /**
     * 记录创建时间
     */
    protected Date gmtCreate;

    /**
     * 记录的修改时间
     */
    protected Date gmtModified;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
