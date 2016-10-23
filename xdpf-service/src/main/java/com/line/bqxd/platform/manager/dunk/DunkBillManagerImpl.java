package com.line.bqxd.platform.manager.dunk;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.DunkBillDO;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;
import com.line.bqxd.platform.dao.DunkBillDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/22.
 */
public class DunkBillManagerImpl implements DunkBillManager{
    @Autowired
    private DunkBillDAO<DunkBillDO> dunkBillDAO;
    @Override
    public boolean inert(DunkBillDO dunkBillDO) {
        long rt=dunkBillDAO.insert(dunkBillDO);
        return rt>0?true:false;
    }

    @Override
    public long selectStatistics(DunkBillQueryDO dunkBillQueryDO) {
        return dunkBillDAO.selectStatistics(dunkBillQueryDO);
    }

    @Override
    public long statisticsConsume(long userId, long concurId) {
        DunkBillQueryDO queryDO = new DunkBillQueryDO();
        queryDO.setConcurPlanId(concurId);
        queryDO.setUserId(userId);
        return selectStatistics(queryDO);
    }

    @Override
    public Long countByQuery(DBBaseQueryDO queryDO) {
        return dunkBillDAO.countByQuery(queryDO);
    }

    @Override
    public List<DunkBillDO> selectByQuery(DBBaseQueryDO queryDO) {
        return dunkBillDAO.selectByQuery(queryDO);
    }
}
