package com.line.bqxd.platform.page;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/25.
 */
public class PageResult <T extends Base>{

    private boolean success=false;

    private DBBaseQueryDO queryDO;

    private String queryJson;

    private List<T> modle;

    private long totalPage;

    private long totalRecord;

    private long currentPage;

    public PageResult() {
    }

    public PageResult(List<T> modle, long totalRecord) {
        this.success=true;
        this.modle = modle;
        this.totalRecord = totalRecord;
    }

    public DBBaseQueryDO getQueryDO() {
        return queryDO;
    }

    public void setQueryDO(DBBaseQueryDO queryDO) {
        this.queryDO = queryDO;
    }

    public List<T> getModle() {
        return modle;
    }

    public void setModle(List<T> modle) {
        this.modle = modle;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public String getQueryJson() {
        return queryJson;
    }

    public void setQueryJson(String queryJson) {
        this.queryJson = queryJson;
    }
}
