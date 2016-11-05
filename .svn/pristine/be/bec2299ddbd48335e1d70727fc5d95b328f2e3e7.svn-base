package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.DunkBillDO;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.dao.DunkBillDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;

import java.util.Map;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class DunkBillDAOImpl extends SimpleDAOImpl implements DunkBillDAO<DunkBillDO> {

    @Override
    public long selectStatistics(DunkBillQueryDO queryDO) {
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix() + ".selectStatistics", queryDO);
        if(o==null){
            return 0;
        }else{
            return (long)o;
        }
    }



    @Override
    public Long countByQuery(DBBaseQueryDO queryDO) {
        if (queryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        return  (Long)getSqlMapClientTemplate().queryForObject(getSqlMapNameSpacePrefix()+".countByQuery", queryDO);
    }






}
