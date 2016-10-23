package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.CleanExtendFeeDO;
import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.query.CleanExtendFeeQueryDO;
import com.line.bqxd.platform.dao.CleanExtendFeeDAO;
import com.line.bqxd.platform.dao.ExtendDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class CleanExtendFeeDAOImpl extends SimpleDAOImpl implements CleanExtendFeeDAO<CleanExtendFeeDO> {

    @Override
    public Long countByQuery(CleanExtendFeeQueryDO cleanExtendFeeQueryDO) {
        if (cleanExtendFeeQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        return (Long)getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",cleanExtendFeeQueryDO);

    }
}
