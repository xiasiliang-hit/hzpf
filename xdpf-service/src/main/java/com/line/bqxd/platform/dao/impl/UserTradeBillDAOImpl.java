package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.UserTradeBillDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserTradeBillDAO;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class UserTradeBillDAOImpl extends SimpleDAOImpl implements UserTradeBillDAO<UserTradeBillDO> {
    @Override
    public long countByQuery(UserTradeBillQueryDO userTradeBillQueryDO) {
        if (userTradeBillQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",userTradeBillQueryDO);
        if(o!=null&&o instanceof Long){
            return ((Long)o).longValue();
        }else{

        }
        return 0;
    }
}
