package com.line.bqxd.platform.manager.extend;

import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.UserActivityDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/7/29.
 */
public interface ExtendManager {

    public boolean insert(ExtendDO extendDO);

    public boolean update(ExtendDO extendDO);

    public ExtendDO selectById(long id);

    public List<ExtendDO> getAll();
}
