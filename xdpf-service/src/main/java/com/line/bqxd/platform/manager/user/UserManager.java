package com.line.bqxd.platform.manager.user;

import com.line.bqxd.platform.client.dataobject.UserAppOpenIdDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserAppOpenIdQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;

import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/16.
 */
public interface UserManager {

    public void updateWXByOpenId(UserDO userDO,boolean isUpdateGroupHead);

    public void updateWXByUnionId(UserDO userDO);

    public long insert(UserDO userDO);

    public long insert(UserDO userDO,long appId);

    public void perfectUser(long userId,String card,String userName,String mobile);

    public void perfectUserBank(long userId,String bankNo,String bankName);

    public UserDO selectByUnionId(String unionId);

    public UserDO selectByOpenId(String openId);

    public UserDO selectByOpenId(String openId,long appid);

    public UserDO selectByUserId(long userId);

    public void delUser(long userId);

    public void recoverUser(long userId);


    /**
     * 根据手机号码查询用户
     * @param mobile
     * @return
     */
    public UserDO selectByMobile(String mobile);

    /**
     * 根据身份证号码查询用户
     * @param card
     * @return
     */
    public UserDO selectByCard(String card);

    /**
     * 根据用户ID数组查询用户列表
     * @param ids
     * @return
     */
    public List<UserDO> selectByIdList(List<Long> ids);

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
     * 根据条件获取用户关系
     * @param userAppOpenIdQueryDO
     * @return
     */
    public List<UserAppOpenIdDO> getList(UserAppOpenIdQueryDO userAppOpenIdQueryDO);

    /**
     * 根据用户ID获取用户基本表的集合
     * @param ids
     * @return
     */
    public Map<Long,UserDO> selectByUserIds(List<Long> ids);

    public boolean updateByOpenid(UserDO userDO);



    public void updateMemberFee(String openId,long fee);

    /**
     * 用户对单个互助充值
     * @param userId
     * @param concurId
     * @param fee
     * @return
     */
    public boolean addBalance(long userId,long concurId,long fee);

    /**
     * 用户对单个互助进行体现
     * @param userId
     * @param concurId
     * @param fee
     * @return
     */
    public boolean reduceBalance(long userId,long concurId,long fee);

    /**
     * 获取用户单个互助的余额
     * @param userId
     * @param concurId
     * @return
     */
    public long getBalance(long userId,long concurId);

    public boolean delete(long userId);

    public String getOpenId(long pfAppid,long userId);



}
