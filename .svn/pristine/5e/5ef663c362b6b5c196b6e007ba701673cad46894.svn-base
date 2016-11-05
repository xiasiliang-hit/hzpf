package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.common.BaseDAOImpl;
import com.line.bqxd.platform.dao.UserDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/4/26.
 */
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {
    //@Autowired
    //private SqlMapClientTemplate sqlMapClientTemplate;

    @Override
    public long insert(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("DAO insert object null");
        }
        if(StringUtils.isNotBlank(userDO.getCard())) {
            userDO.setBirth(BizUtils.getBirth(userDO.getCard()));
            userDO.setSex(BizUtils.getSex(userDO.getCard()));
        }
        Object object = getSqlMapClientTemplate().insert("user.insert", userDO);
        if (object != null && object instanceof Long) {
            return (Long) object;
        }
        return 0;
    }

    @Override
    public boolean update(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("DAO update object null");
        }
        if (userDO.getUserId() <= 0) {
            throw new IllegalArgumentException("update userId less than zero id=" + userDO.getUserId());

        }
        //userDO.setBirth(BizUtils.getBirth(userDO.getCard()));
        //userDO.setSex(BizUtils.getSex(userDO.getCard()));
        int result = getSqlMapClientTemplate().update("user.update", userDO);
        return result > 0 ? true : false;
    }

    @Override
    public boolean updateByOpenid(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("DAO updateByOpenid object null");
        }
        if (StringUtils.isBlank(userDO.getOpenid())) {
            throw new IllegalArgumentException("updateByOpenid openId is null");

        }
        if(StringUtils.isNotBlank(userDO.getCard())) {
            userDO.setBirth(BizUtils.getBirth(userDO.getCard()));
            userDO.setSex(BizUtils.getSex(userDO.getCard()));
        }
        int result = getSqlMapClientTemplate().update("user.updateByOpenId", userDO);
        return result > 0 ? true : false;
    }

    @Override
    public boolean updateWXByOpenid(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("DAO updateByOpenid object null");
        }
        if (StringUtils.isBlank(userDO.getOpenid())) {
            throw new IllegalArgumentException("updateWXByOpenid openId is null");

        }
        int result = getSqlMapClientTemplate().update("user.updateWXByOpenId", userDO);
        return result > 0 ? true : false;
    }

    @Override
    public boolean updateWXByUnionid(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("DAO updateWXByUnionid object null");
        }
        if (StringUtils.isBlank(userDO.getUnionid())) {
            throw new IllegalArgumentException("updateWXByUnionid unionid is null");

        }
        int result = getSqlMapClientTemplate().update("user.updateWXByUnionId", userDO);
        return result > 0 ? true : false;
    }


    @Override
    public UserDO selectByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new IllegalArgumentException("selectByOpenId openId is null");

        }
        List<UserDO> list = getSqlMapClientTemplate().queryForList("user.selectByOpenId", openId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserDO selectByUserId(long userId) {
        if (userId<=0) {
            throw new IllegalArgumentException("selectByUserId userId less than zeor, userId="+userId);

        }
        List<UserDO> list = getSqlMapClientTemplate().queryForList("user.selectByUserId", userId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserDO selectByUnionId(String unionId) {
        if (StringUtils.isBlank(unionId)) {
            throw new IllegalArgumentException("selectByUnionId unionId is null");

        }
        List<UserDO> list = getSqlMapClientTemplate().queryForList("user.selectByUnionId", unionId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UserDO> selectByQueryPage(UserQueryDO userQueryDO) {
        if (userQueryDO == null) {
            throw new NullPointerException("DAO selectByQueryPage object null");
        }
        if(userQueryDO.isInitPageNum()){
            userQueryDO.initPageNum();
        }
        if(userQueryDO.isInitPageSize()){
            userQueryDO.isInitPageSize();
        }
        userQueryDO.initStartNum();
        List<UserDO> list = getSqlMapClientTemplate().queryForList("user.selectByQueryPage", userQueryDO);
        return list;

    }

    @Override
    public Long countByQueryPage(UserQueryDO userQueryDO) {
        if (userQueryDO == null) {
            throw new NullPointerException("DAO countByQueryPage object null");
        }

        return  (Long)getSqlMapClientTemplate().queryForObject("user.countByQueryPage", userQueryDO);
    }

    @Override
    public List<UserDO> selectByQuery(UserQueryDO userQueryDO) {
        if (userQueryDO == null) {
            throw new NullPointerException("DAO selectByQuery object null");
        }
        List<UserDO> list = getSqlMapClientTemplate().queryForList("user.selectByQuery", userQueryDO);
        return list;
    }



    @Override
    public boolean delete(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("selectById id less than zero id=" + userId);
        }
        int result = getSqlMapClientTemplate().delete("user.delete", userId);
        return result > 0 ? true : false;
    }


}
