package com.line.bqxd.platform.manager.extend.impl;

import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.UserActivityDO;
import com.line.bqxd.platform.dao.ExtendDAO;
import com.line.bqxd.platform.dao.UserActivityDAO;
import com.line.bqxd.platform.manager.extend.ExtendManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huangjianfei on 16/7/29.
 */
public class ExtendManagerImpl implements ExtendManager {

    @Autowired
    private ExtendDAO<ExtendDO> extendDAO;

    @Override
    public boolean insert(ExtendDO extendDO) {
        long rt= extendDAO.insert(extendDO);
        return rt>0?true:false;
    }

    @Override
    public boolean update(ExtendDO extendDO) {
        return extendDAO.update(extendDO);
    }

    @Override
    public ExtendDO selectById(long id) {
        return (ExtendDO)extendDAO.selectById(id);
    }

    @Override
    public List<ExtendDO> getAll() {
        return extendDAO.selectAll();
    }
}
