package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.UserTradePayDO;

import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;

import com.line.bqxd.platform.dao.UserTradePayDAO;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class UserTradePayDAOImpl extends SimpleDAOImpl implements UserTradePayDAO<UserTradePayDO> {
    @Override
    public int countByQuery(UserTradePayQueryDO userTradePayQueryDO) {
        if (userTradePayQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o = getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix() + ".countByQuery", userTradePayQueryDO);
        if (o != null && o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {

        }
        return 0;
    }

    @Override
    public long sumFeeByUserId(long userId) {
        Object o = getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix() + ".sumFeeByUserId", userId);
        if (o != null && o instanceof Long) {
            return ((Long) o).intValue();
        } else {

        }
        return 0;
    }
}
