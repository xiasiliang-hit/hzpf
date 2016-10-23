package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.ClaimDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserClaimApplyDAO;
import com.sun.org.apache.xpath.internal.operations.Number;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class UserClaimApplyDAOImpl extends SimpleDAOImpl implements UserClaimApplyDAO<ClaimDO> {
    @Override
    public int countByQuery(UserClaimApplyQueryDO userClaimApplyQueryDO) {
        if (userClaimApplyQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",userClaimApplyQueryDO);
        if (o != null && o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {

        }
        return 0;
    }
}
