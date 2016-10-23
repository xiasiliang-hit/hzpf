package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;

import java.util.Map;

/**
 * Created by huangjianfei on 16/5/10.
 */
public interface DunkBillDAO<T>  extends BaseDAO {

    public long selectStatistics(DunkBillQueryDO dunkBillQueryDO);



    /**
     * 根据条件用户数据统计
     * @param queryDO
     * @return
     */
    public Long countByQuery(DBBaseQueryDO queryDO);

}
