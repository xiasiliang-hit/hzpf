package com.line.bqxd.platform.page;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/25.
 */
public class PageWrap {

    public PageResult getPageResult(DBBaseQueryDO queryDO, PageHandler pageHandler) {
        if (pageHandler == null) {
            throw new NullPointerException("pageHandler null");
        }
        if (queryDO == null) {
            throw new NullPointerException("queryDO null");
        }
        if (queryDO.isInitPageNum()) {
            queryDO.initPageNum();
        }
        if (queryDO.isInitPageSize()) {
            queryDO.initPageSize();
        }
        long count = pageHandler.getPageCount(queryDO);
        List<? extends Base> list = pageHandler.getPageList(queryDO);

        PageResult<? extends Base> pageResult = new PageResult(list, count);
        if (count > 0) {
            long totalPage = 0;
            if (count % queryDO.getPageSize() != 0) {
                totalPage = count / queryDO.getPageSize() + 1;
            } else {
                totalPage = count / queryDO.getPageSize();
            }
            pageResult.setTotalPage(totalPage);
            pageResult.setTotalRecord(count);
        }
        pageResult.setCurrentPage(queryDO.getPageNum());
        return pageResult;
    }
}
