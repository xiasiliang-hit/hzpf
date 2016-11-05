package com.line.bqxd.platform.client.common;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/26.
 */
public class DBBaseQueryDO extends Base implements Serializable {
    private static final long serialVersionUID = -8892064530024027797L;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final int DEFAULT_FIRST_PAGE_NUM = 1;
    /**
     * 页数
     */
    private int pageNum = 1;
    /**
     * 每页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    private int startNum=0;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void initPageNum() {
        pageNum = DEFAULT_FIRST_PAGE_NUM;
    }

    public void initPageSize() {
        pageSize = DEFAULT_PAGE_SIZE;
    }

    public boolean isInitPageNum() {
        return this.pageNum < DEFAULT_FIRST_PAGE_NUM ? true : false;
    }

    public boolean isInitPageSize() {
        return this.pageSize < DEFAULT_PAGE_SIZE ? true : false;
    }

    public void initStartNum(){
        if (startNum <= 0) {
            if (isInitPageSize()) {
                initPageSize();
            }
            if (isInitPageNum()) {
                initPageNum();
            }

            startNum = (pageNum-1) * pageSize;
        }
    }

    public boolean verifyParameter(){
        return true;
    }
}
