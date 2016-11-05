package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.RegMobileTempDO;
import com.line.bqxd.platform.dao.RegMobileTempDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class RegMobileTempDAOImpl extends SimpleDAOImpl implements RegMobileTempDAO<RegMobileTempDO> {

    @Override
    public RegMobileTempDO selectByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("selectByMobile argument illegal ,mobile blank=");
        }
        List<RegMobileTempDO> list = getSqlMapClientTemplate().queryForList(getSqlMapNameSpacePrefix() + ".selectByMobile", mobile);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean deleteByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("selectByMobile argument illegal ,mobile blank=");
        }

        int result = getSqlMapClientTemplate().delete(getSqlMapNameSpacePrefix() + ".deleteByMobile", mobile);
        return result > 0 ? true : false;
    }
}
