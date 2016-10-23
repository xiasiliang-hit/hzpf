package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.ConcurRemindDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.ConcurRemindQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.ConcurRemindDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserTradePayDAO;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class ConcurRemindDAOImpl extends SimpleDAOImpl implements ConcurRemindDAO<ConcurRemindDO> {
    @Override
    public int countByQuery(ConcurRemindQueryDO concurRemindQueryDO) {
        if (concurRemindQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",concurRemindQueryDO);
        if(o!=null&&o instanceof Integer){
            return ((Integer)o).intValue();
        }else{

        }
        return 0;
    }
}
