package com.line.bqxd.platform.manager.concur;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.ConcurPlanQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.common.BizResult;

import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/16.
 */
public interface ConcurManager {


    /**
     * 加入互助
     * @param concurId
     * @param userDO 运营实体用户
     * @param list 参数互助用户列表
     * @return 返回用户参加互助列表ID
     * @throws Exception
     */
    public BizResult<List<Long>> addConcur(long concurId,UserDO userDO,  List<UserConcurRelationDO> list) throws Exception;

    /**
     * 分页条件查询互助计划
     * @param concurPlanQueryDO
     * @return
     */
    public List<ConcurPlanDO> selectByQueryPage(ConcurPlanQueryDO concurPlanQueryDO);

    /**
     * 设置用户保障正常
     * @param ids
     */
    public boolean setEnsureNormally(String[] ids);

    /**
     *
     * @param concurId 互助计划ID
     * @param ratio 互助系数
     */
    public void updateCacheStatistics(long concurId, long ratio);

    /**
     * 获取互助总用户数
     * @param concurId
     * @return
     */
    public long getConcurPlanSumUserQuantity(long concurId);

    /**
     * 获取大病互助总分摊系数
     * @param concurId
     * @return
     */
    public long getConcurPlanRatioSumQuantity(long concurId);

    /**
     * 根据互助ID获取此互助的资金池
     * @param concurId
     * @return
     */
    public long getConcurPlanMoneySumQuantity(long concurId);

    /**
     * 获取全部的互助列表
     * @return
     */
    public Map<Long, ConcurPlanDO> getConcurPlanAll();

    /**
     * 根据互助计划ID获取互助对象
     * @param concurPlanId
     * @return
     */
    public ConcurPlanDO getConcurPlanById(long concurPlanId);

    /**
     * 根据互助计划ID获取小组数量
     * @param concurPlanId
     * @return
     */
    public int getConcurPlanGroupCountById(long concurPlanId);

    /**
     * 根据互助计划的ID,获取全部的小组列表
     * @return
     */
    //public ConcurPlanGroupDO getConcurPlanGroupByConcurPlanId(long concurPlanGroupId);

    /**
     * 根据互助ID和用户ID,获取用户参加互助的关系
     * @param userId
     * @param concurPlanId
     * @return
     */
    public List<UserConcurRelationDO> getUserConcurRelation(long userId,long concurPlanId);

    /**
     * 根据互助ID和用户ID,获取用户可以申请理赔的关系
     * @param userId
     * @param concurPlanId
     * @return
     */
    public List<UserConcurRelationDO> getCanCliamUserConcurRelation(long userId,long concurPlanId);

    /**
     * 判断用户是否已经加入互助计划
     * @param userId
     * @param concurPlanId
     * @return
     */
    public boolean isUserEXSIT(long userId,long concurPlanId);

    /**
     * 清除缓存
     */
    public void clean();

    /**
     * 分页条件查询
     * @param userConcurRelationQueryDO
     * @return
     */
    public List<UserConcurRelationDO> selectByQueryPage(UserConcurRelationQueryDO userConcurRelationQueryDO);


    /**
     * 根据条件用户数据统计
     * @param userConcurRelationQueryDO
     * @return
     */
    public Long countByQueryPage(UserConcurRelationQueryDO userConcurRelationQueryDO);


    /**
     * 获取之前的加入互助的用户数量
     * @param concurPlanId
     * @param joinTime
     * @return
     */
    public long getBeforeUserCountByConcurPlianID(long concurPlanId,String joinTime);


    /**
     * 获取之前的加入互助的用户列表
     * @param concurPlanId
     * @param joinTime
     * @return
     */
    public List<UserConcurRelationDO> getBeforeUserListByConcurPlianID(long concurPlanId,String joinTime);
    /**
     * 删除已经参加用户的关系
     * @param id
     * @return
     */
    public boolean delUserRelation(long id);

    /**
     * 失效用户保障
     * @param id
     * @return
     */
    public boolean invalidUserRelation(long id);

    public boolean refuseClaimRelation(long id);
    /**
     * 用户理赔
     * @param id
     * @return
     */
    public boolean claimUserRelation(long id);


    public boolean setStatusNormally(long id);

    public UserConcurRelationDO selectUserConcurRelation(long id);


    public List<UserConcurRelationDO> selectUserConcurRelationByIdS(List<Long> ids);


    public Long statisticsRatio(long concurId,String startJoinDate);
}
