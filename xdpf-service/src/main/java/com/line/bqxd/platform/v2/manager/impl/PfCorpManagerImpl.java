package com.line.bqxd.platform.v2.manager.impl;

import com.line.bqxd.platform.client.dataobject.PfCorpDO;
import com.line.bqxd.platform.dao.PfCorpDAO;
import com.line.bqxd.platform.v2.manager.PfCorpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/9.
 */
public class PfCorpManagerImpl implements PfCorpManager {
    private static final Logger logger = LoggerFactory.getLogger(PfCorpManagerImpl.class);

    @Autowired
    private PfCorpDAO<PfCorpDO> pfCorpDAO;

    @Override
    public boolean insert(PfCorpDO pfCorpDO) {
        return pfCorpDAO.insert(pfCorpDO)>0?true:false;
    }

    @Override
    public boolean update(PfCorpDO pfCorpDO) {
        return pfCorpDAO.update(pfCorpDO);
    }

    @Override
    public PfCorpDO getPfCorp(long corpId) {
        return  (PfCorpDO) pfCorpDAO.selectById(corpId);
    }

    @Override
    public List<PfCorpDO> getAll() {
        return pfCorpDAO.selectAll();
    }
}
