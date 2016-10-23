package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;

/**
 * Created by huangjianfei on 16/5/3.
 */
public interface UserTradeBillDAO<T> extends BaseDAO{
    public long countByQuery(UserTradeBillQueryDO userTradeBillQueryDO);

}
