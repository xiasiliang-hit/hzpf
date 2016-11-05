package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.UserAdminDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserAdminDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class UserAdminDAOImpl extends SimpleDAOImpl implements UserAdminDAO<UserAdminDO> {

    @Override
    public UserAdminDO getUserAdminByLoginName(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            throw new IllegalArgumentException("getUserAdminByLoginName loginName is null");

        }
        List<UserAdminDO> list = getSqlMapClientTemplate().queryForList(this.getSqlMapNameSpacePrefix()+".getUserAdminByLoginName", loginName);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


}
