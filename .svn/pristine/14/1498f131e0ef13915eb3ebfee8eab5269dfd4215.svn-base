package com.line.bqxd.platform.manager.dunk;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.DunkBillDO;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/22.
 */
public interface DunkBillManager {

    public boolean inert(DunkBillDO dunkBillDO);

    public long selectStatistics(DunkBillQueryDO dunkBillQueryDO);

    /**
     * 统计用户在这个互助计划中消费的费用
     * @param userId
     * @param concurId
     * @return
     */
    public long statisticsConsume(long userId,long concurId);

    /**
     * 根据条件用户数据统计
     * @param queryDO
     * @return
     */
    public Long countByQuery(DBBaseQueryDO queryDO);

    public List<DunkBillDO> selectByQuery(DBBaseQueryDO queryDO);

}
