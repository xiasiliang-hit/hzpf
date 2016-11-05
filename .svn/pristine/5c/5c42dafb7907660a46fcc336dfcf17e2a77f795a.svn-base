package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserTradeFillDAO;
import com.line.bqxd.platform.dao.UserTradePayDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class UserTradeFillDAOImpl extends SimpleDAOImpl implements UserTradeFillDAO<UserTradeFillDO> {
    @Override
    public Long countByQuery(UserTradeFillQueryDO userTradeFillQueryDO) {
        if (userTradeFillQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        return (Long)getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",userTradeFillQueryDO);

    }

    @Override
    public boolean updateByTransactionId(UserTradeFillDO userTradeFillDO) {
        if (userTradeFillDO == null) {
            throw new NullPointerException("DAO updateByTransactionId object null");
        }
        int result = getSqlMapClientTemplate().update(this.getSqlMapNameSpacePrefix() + ".updateByTransactionId", userTradeFillDO);
        return result > 0 ? true : false;
    }

    @Override
    public UserTradeFillDO selectByTransactionId(String transactionId) {
        if (StringUtils.isBlank(transactionId)) {
            throw new IllegalArgumentException("selectByTransactionId transactionId is blank");
        }
        List<UserTradeFillDO> list = getSqlMapClientTemplate().queryForList(getSqlMapNameSpacePrefix() + ".selectByTransactionId", transactionId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
