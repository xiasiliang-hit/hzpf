package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/7/27.
 */
public interface UserBalanceDAO<T> extends BaseDAO {

    /**
     * 根据条件统计用户的余额
     * @param queryDO
     * @return
     */
    public Long statisticsBalanceByQuery(DBBaseQueryDO queryDO);

}
