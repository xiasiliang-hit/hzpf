package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;

/**
 * Created by huangjianfei on 16/5/3.
 */
public interface UserTradePayDAO<T> extends BaseDAO{
    public int countByQuery(UserTradePayQueryDO userTradePayQueryDO);

    public long sumFeeByUserId(long userId);

}
