package com.line.bqxd.platform.v2.manager;

import com.line.bqxd.platform.client.dataobject.PfCorpDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/9.
 */
public interface PfCorpManager {
    public boolean insert(PfCorpDO pfCorpDO);

    public boolean update(PfCorpDO pfCorpDO);

    public PfCorpDO getPfCorp(long corpId);

    public List<PfCorpDO> getAll();

}
