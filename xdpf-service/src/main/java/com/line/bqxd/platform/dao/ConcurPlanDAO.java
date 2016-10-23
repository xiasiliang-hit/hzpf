package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserDO;

import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/3.
 */
public interface ConcurPlanDAO<T> extends BaseDAO{

    public List<T> ddd(Map<String,String> map);

}
