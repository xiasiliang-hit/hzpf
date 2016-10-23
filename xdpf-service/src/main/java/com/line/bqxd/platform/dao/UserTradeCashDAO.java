package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.UserTradeCashDO;

/**
 * Created by danny on 10/21/16.
 */
public interface UserTradeCashDAO<T> extends BaseDAO {

     long insert(UserTradeCashDO userTradeCashDO);

}
