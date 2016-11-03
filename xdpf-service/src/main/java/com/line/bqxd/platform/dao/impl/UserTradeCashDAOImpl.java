package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.UserTradeCashDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserTradeCashDAO;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class UserTradeCashDAOImpl extends SimpleDAOImpl implements UserTradeCashDAO<UserTradeCashDO> {
    /*
    @Override
    public int countByQuery(UserTradeCashQueryDO userTradeCashQueryDO) {
        if (userTradeCashQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",userTradeCashQueryDO);
        if(o!=null&&o instanceof Integer){
            return ((Integer)o).intValue();
        }else{

        }
        return 0;
    }
    */
			
	@Override
    public long insert(UserTradeCashDO userTradeCashDO) {
        if (userTradeCashDO == null) {
            throw new NullPointerException("DAO insert object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".insert",userTradeCashDO);
        if(o!=null&&o instanceof Integer){
            return ((Integer)o).intValue();
        }else{

        }
        return 0;
    }
		
}
