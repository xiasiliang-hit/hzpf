package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;

import java.util.Map;

/**
 * Created by huangjianfei on 16/5/10.
 */
public interface UserConcurRelationDAO<T>  extends BaseDAO {

    public Map<String,Long> selectStatistics(UserConcurRelationQueryDO userConcurRelationQueryDO);



    /**
     * 根据条件用户数据统计
     * @param queryDO
     * @return
     */
    public Long countByQuery(DBBaseQueryDO queryDO);

    /**
     * 更新保障用状态
     * @param id
     * @return
     */
    public boolean setStatusNormally(long id);



    public Long statisticsRatio(DBBaseQueryDO queryDO);

}
