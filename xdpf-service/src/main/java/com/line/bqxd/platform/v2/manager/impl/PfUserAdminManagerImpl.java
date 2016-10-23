package com.line.bqxd.platform.v2.manager.impl;

import com.line.bqxd.platform.client.dataobject.PfUserAdminDO;
import com.line.bqxd.platform.client.dataobject.query.PfUserAdminQueryDO;
import com.line.bqxd.platform.dao.PfUserAdminDAO;
import com.line.bqxd.platform.v2.manager.PfUserAdminManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/9.
 */
public class PfUserAdminManagerImpl implements PfUserAdminManager {
    private static final Logger logger = LoggerFactory.getLogger(PfUserAdminManagerImpl.class);

    @Autowired
    private PfUserAdminDAO<PfUserAdminDO> pfUserAdminDAO;

    @Override
    public PfUserAdminDO getPfUserAdminDOByLoginName(String loginName) {
        PfUserAdminQueryDO pfUserAdminQueryDO = new PfUserAdminQueryDO();
        pfUserAdminQueryDO.setLoginName(loginName);
        List<PfUserAdminDO> list = pfUserAdminDAO.selectByQuery(pfUserAdminQueryDO);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public boolean insert(PfUserAdminDO pfUserAdminDO) {
        long rt = pfUserAdminDAO.insert(pfUserAdminDO);
        return rt > 0 ? true : false;
    }

    @Override
    public boolean update(PfUserAdminDO pfUserAdminDO) {
        return pfUserAdminDAO.update(pfUserAdminDO);
    }
}
