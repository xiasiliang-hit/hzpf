package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.UserBalanceDO;
import com.line.bqxd.platform.dao.ExtendDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserBalanceDAO;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class UserBalanceDAOImpl extends SimpleDAOImpl implements UserBalanceDAO<UserBalanceDO> {

    @Override
    public Long statisticsBalanceByQuery(DBBaseQueryDO queryDO) {
        if (queryDO == null) {
            throw new NullPointerException("DAO statisticsBalanceByQuery object null");
        }

        Long rt= (Long) getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix() + ".statisticsBalanceByQuery", queryDO);
        return rt!=null?rt:0;
    }
}
