package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;

/**
 * Created by huangjianfei on 16/7/27.
 */
public interface PfWeixinAuthDAO<T> extends BaseDAO {

    public boolean updateByAppId(T t);

    public PfWeixinAuthDO selectByAppid(String appid);
}
