package com.line.bqxd.platform.dao;


import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/4/26.
 */
public interface UserDAO {
    /**
     * 增加用户
     * @param userDO
     * @return
     */
    public long insert(UserDO userDO);

    /**
     * 修改用户信息根据用户ID查询
     * @param userDO
     * @return
     */
    public boolean update(UserDO userDO);

    /**
     * 修改用户信息
     * @param userDO
     * @return
     */
    public boolean updateByOpenid(UserDO userDO);

    /**
     * 修改微信同步用户信息
     * @param userDO
     * @return
     */
    public boolean updateWXByOpenid(UserDO userDO);

    /**
     * 修改微信同步用户信息
     * @param userDO
     * @return
     */
    public boolean updateWXByUnionid(UserDO userDO);

    /**
     * 根据用户OPENID查询用户
     * @param openId
     * @return
     */
    public UserDO selectByOpenId(String openId);

    /**
     * 根据用户userId查询用户
     * @param userId
     * @return
     */
    public UserDO selectByUserId(long userId);

    /**
     * 根据用户UNIONID查询用户
     * @param unionId
     * @return
     */
    public UserDO selectByUnionId(String unionId);

    /**
     * 分页条件查询
     * @param userQueryDO
     * @return
     */
    public List<UserDO> selectByQueryPage(UserQueryDO userQueryDO);


    /**
     * 根据条件用户数据统计
     * @param userQueryDO
     * @return
     */
    public Long countByQueryPage(UserQueryDO userQueryDO);

    /**
     * 条件查询,不分页返回
     * @param userQueryDO
     * @return
     */
    public List<UserDO> selectByQuery(UserQueryDO userQueryDO);



    /**
     * 删除用户
     * @param userId
     * @return
     */
    public boolean delete(long userId);
}
