package com.line.bqxd.platform.page;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.common.DBBaseDO;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/25.
 */
public interface PageHandler<T>{

    public List<? extends Base> getPageList(T  queryDO);

    public long getPageCount(T queryDO);

}
