package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.query.CleanExtendFeeQueryDO;

/**
 * Created by huangjianfei on 16/7/27.
 */
public interface CleanExtendFeeDAO<T> extends BaseDAO {

    public Long countByQuery(CleanExtendFeeQueryDO cleanExtendFeeQueryDO);

}
