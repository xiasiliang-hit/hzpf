package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.dao.PfWeixinAuthDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class PfWeixinAuthDAOImpl extends SimpleDAOImpl implements PfWeixinAuthDAO<PfWeixinAuthDO> {

    @Override
    public boolean updateByAppId(PfWeixinAuthDO pfWeixinAuthDO) {
        if (pfWeixinAuthDO == null) {
            throw new NullPointerException("DAO update object null");
        }
        int result = getSqlMapClientTemplate().update(getSqlMapNameSpacePrefix()+".updateByAppId", pfWeixinAuthDO);
        return result > 0 ? true : false;
    }

    @Override
    public PfWeixinAuthDO selectByAppid(String appid) {
        if (StringUtils.isBlank(appid)) {
            throw new IllegalArgumentException("selectByAppid appid is blank");
        }
        List<PfWeixinAuthDO> list = getSqlMapClientTemplate().queryForList(getSqlMapNameSpacePrefix() + ".selectByAppid", appid);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
