package com.line.bqxd.platform.manager.concur.impl;

import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.ConcurPlanQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserBalanceQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.dao.ConcurPlanDAO;
import com.line.bqxd.platform.dao.UserBalanceDAO;
import com.line.bqxd.platform.dao.UserConcurRelationDAO;
import com.line.bqxd.platform.dao.WXAttentionDAO;
import com.line.bqxd.platform.manager.CycleLife;
import com.line.bqxd.platform.manager.CycleLifeManager;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.concur.ConcurStatistics;
import com.line.bqxd.platform.manager.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.line.bqxd.platform.client.constant.Status.EnsureStatus;

/**
 * Created by huangjianfei on 16/5/16.
 */
public class ConcurManagerImpl implements ConcurManager, CycleLife {

    private static Logger logger = LoggerFactory.getLogger(ConcurManagerImpl.class);

    private final static List<Integer> NORMALLYSTATUS= new ArrayList<Integer>();

    static {
        NORMALLYSTATUS.add(EnsureStatus.NORMALLY.getValue());
        NORMALLYSTATUS.add(EnsureStatus.WRAN.getValue());
        NORMALLYSTATUS.add(EnsureStatus.CLIAM.getValue());
        NORMALLYSTATUS.add(EnsureStatus.CLIAMREFUSE.getValue());
    }

    private final static List<Integer> CANCLIAMSTATUS= new ArrayList<Integer>();

    static {
        CANCLIAMSTATUS.add(EnsureStatus.NORMALLY.getValue());
        CANCLIAMSTATUS.add(EnsureStatus.WRAN.getValue());
        CANCLIAMSTATUS.add(EnsureStatus.CLIAMREFUSE.getValue());
    }

    //计划分组统计
    private Map<Long, ConcurStatistics> map = new HashMap<Long, ConcurStatistics>();

    private Map<Long, ConcurPlanDO> cacheConcurMap = new HashMap<Long, ConcurPlanDO>();

    private Map<Long, Integer> cacheGroupCountMap = new HashMap<Long, Integer>();

    @Resource
    private UserManager userManager;

    @Resource
    private UserConcurRelationDAO<UserConcurRelationDO> userConcurRelationDAO;

    @Resource
    private ConcurPlanDAO<ConcurPlanDO> concurPlanDAO;

    @Resource
    private UserBalanceDAO<UserBalanceDO> userBalanceDAO;

    @Resource
    private CycleLifeManager cycleLifeManager;

    @Resource
    private RunEnv runEnv;

    @Override
    public BizResult<List<Long>> addConcur(long concurId,UserDO userDO, List<UserConcurRelationDO> list) throws Exception {
        if (userDO == null) {
            throw new NullPointerException("add Concur plian is null,userDO");
        }
        ConcurPlanDO concurPlanDO = (ConcurPlanDO) concurPlanDAO.selectById(concurId);
        if (concurPlanDO == null) {
            throw new NullPointerException("concurPlanDAO.selectById is null,concurId=" + concurId);

        }
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("add Concur plian is null,list");
        }
        String currentDate=DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date());
        List<Long> rtList= new ArrayList<Long>();
        try {
            for (UserConcurRelationDO userConcurRelationDO : list) {
                userConcurRelationDO.setConcurPlanId(concurPlanDO.getId());
                userConcurRelationDO.setUserId(userDO.getUserId());
                userConcurRelationDO.setJoinTime(currentDate);
                int ratio=BizUtils.getRatio(userConcurRelationDO.getWeight(),userConcurRelationDO.getChildWeek());
                userConcurRelationDO.setRatio(ratio*100);
                if(runEnv.isDev()){
                    //测试环境用1分测试
                    userConcurRelationDO.setFirstFillMoney(1);
                }else {
                    if (ratio == 1) {
                        userConcurRelationDO.setFirstFillMoney(50000);
                    } else if (ratio == 5) {
                        userConcurRelationDO.setFirstFillMoney(200000);
                    } else if (ratio == 9) {
                        userConcurRelationDO.setFirstFillMoney(300000);
                    }
                }
                userConcurRelationDO.setUpperLimit(30 * ratio * 100);

                if (userConcurRelationDO.getFirstFillMoney() <= 0) {
                    userConcurRelationDO.setEnsureStatus(EnsureStatus.NORMALLY.getValue());
                } else {
                    //需要进行首次充值
                    userConcurRelationDO.setEnsureStatus(EnsureStatus.NEW.getValue());
                }
                userConcurRelationDO.setEnsureStartTime(BizUtils.getEnsureStartTime(userConcurRelationDO.getBirthDay(), currentDate));

                long rt = userConcurRelationDAO.insert(userConcurRelationDO);
                rtList.add(rt);
            }
            return new BizResult(rtList,true);
        }catch (Exception e){
            logger.error("addConcur error",e);
            return new BizResult(ResultEnum.USER_JOIN_CONCUR_FAIL);
        }

    }

    @Override
    public List<ConcurPlanDO> selectByQueryPage(ConcurPlanQueryDO concurPlanQueryDO) {
        return concurPlanDAO.selectByQuery(concurPlanQueryDO);
    }

    @Override
    public boolean setEnsureNormally(String[] ids) {
        if(ids==null||ids.length==0){
            return false;
        }
        boolean result = false;
        try {
            for (String idStr : ids) {
                result = this.setStatusNormally(Long.parseLong(idStr));
                if (result == false) {
                    break;
                }
            }
        }catch (Exception e){
            logger.error("setStatusNormally error",e);
        }
        return result;
    }

    @Override
    public void updateCacheStatistics(long concurId,  long ratio) {

    }

    @Override
    public long getConcurPlanSumUserQuantity(long concurId) {
        UserConcurRelationQueryDO queryDO =new UserConcurRelationQueryDO();
        queryDO.setConcurPlanId(concurId);
        queryDO.setEnsureStatusList(NORMALLYSTATUS);
        return userConcurRelationDAO.countByQuery(queryDO);
    }

    @Override
    public long getConcurPlanRatioSumQuantity(long concurId) {
        return 0;
    }

    @Override
    public long getConcurPlanMoneySumQuantity(long concurId) {
        UserBalanceQueryDO queryDO = new UserBalanceQueryDO();
        queryDO.setConcurId(concurId);
        return userBalanceDAO.statisticsBalanceByQuery(queryDO);
    }


    @Override
    public Map<Long, ConcurPlanDO> getConcurPlanAll() {
        if (CollectionUtils.isEmpty(cacheConcurMap)) {
            synchronized (this) {
                List<ConcurPlanDO> list = concurPlanDAO.selectAll();
                if (CollectionUtils.isEmpty(list)) {
                    for (ConcurPlanDO concurPlanDO : list) {
                        cacheConcurMap.put(concurPlanDO.getId(), concurPlanDO);
                    }
                }
            }
        }
        return cacheConcurMap;
    }

    @Override
    public ConcurPlanDO getConcurPlanById(long concurPlanId) {
        Map<Long, ConcurPlanDO> map = getConcurPlanAll();
        if (!CollectionUtils.isEmpty(map)) {
            return map.get(concurPlanId);
        } else {
            return null;
        }
    }

    @Override
    public int getConcurPlanGroupCountById(long concurPlanId) {
        Integer count = cacheGroupCountMap.get(concurPlanId);
        return count != null ? count.intValue() : 0;
    }


    @Override
    public List<UserConcurRelationDO> getUserConcurRelation(long userId, long concurPlanId) {
        UserConcurRelationQueryDO userConcurRelationQuery = new UserConcurRelationQueryDO();
        userConcurRelationQuery.setConcurPlanId(concurPlanId);
        userConcurRelationQuery.setUserId(userId);
        userConcurRelationQuery.setEnsureStatusList(NORMALLYSTATUS);
        List<UserConcurRelationDO> list = userConcurRelationDAO.selectByQuery(userConcurRelationQuery);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<UserConcurRelationDO> getCanCliamUserConcurRelation(long userId, long concurPlanId) {
        UserConcurRelationQueryDO userConcurRelationQuery = new UserConcurRelationQueryDO();
        userConcurRelationQuery.setConcurPlanId(concurPlanId);
        userConcurRelationQuery.setUserId(userId);
        userConcurRelationQuery.setEnsureStatusList(CANCLIAMSTATUS);
        List<UserConcurRelationDO> list = userConcurRelationDAO.selectByQuery(userConcurRelationQuery);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public boolean isUserEXSIT(long userId, long concurPlanId) {
        return getUserConcurRelation(userId, concurPlanId) != null ? true : false;
    }

    @Override
    public void clean() {
        cacheConcurMap = new HashMap<Long, ConcurPlanDO>();

        init();
    }


    @Override
    public List<UserConcurRelationDO> selectByQueryPage(UserConcurRelationQueryDO userConcurRelationQueryDO) {
        if (userConcurRelationQueryDO == null) {
            userConcurRelationQueryDO = new UserConcurRelationQueryDO();
            userConcurRelationQueryDO.initStartNum();
        }
        return userConcurRelationDAO.selectByQuery(userConcurRelationQueryDO);
    }

    @Override
    public Long countByQueryPage(UserConcurRelationQueryDO userConcurRelationQueryDO) {
        if (userConcurRelationQueryDO == null) {
            userConcurRelationQueryDO = new UserConcurRelationQueryDO();
        }
        return userConcurRelationDAO.countByQuery(userConcurRelationQueryDO);
    }

    @Override
    public long getBeforeUserCountByConcurPlianID(long concurPlanId, String joinTime) {
        UserConcurRelationQueryDO queryDO = new UserConcurRelationQueryDO();
        queryDO.setConcurPlanId(concurPlanId);
        queryDO.setEndJoinTime(joinTime);
        queryDO.setEnsureStatusList(NORMALLYSTATUS);
        return userConcurRelationDAO.countByQuery(queryDO);
    }

    @Override
    public List<UserConcurRelationDO> getBeforeUserListByConcurPlianID(long concurPlanId, String joinTime) {
        long count = getBeforeUserCountByConcurPlianID(concurPlanId, joinTime);
        if (count <= 0) {
            return null;
        }
        UserConcurRelationQueryDO queryDO = new UserConcurRelationQueryDO();
        queryDO.setConcurPlanId(concurPlanId);
        queryDO.setEndJoinTime(joinTime);
        queryDO.setEnsureStatusList(NORMALLYSTATUS);
        queryDO.setPageSize((int) count);
        return userConcurRelationDAO.selectByQuery(queryDO);
    }

    @Override
    public boolean delUserRelation(long id) {
        return userConcurRelationDAO.delete(id);
    }

    @Override
    public boolean invalidUserRelation(long id) {
        UserConcurRelationDO userConcurRelationDO= selectUserConcurRelation(id);
        userConcurRelationDO.setEnsureStatus(EnsureStatus.INVALID.getValue());
        return userConcurRelationDAO.update(userConcurRelationDO);
    }

    @Override
    public boolean refuseClaimRelation(long id) {
        UserConcurRelationDO userConcurRelationDO= selectUserConcurRelation(id);
        userConcurRelationDO.setEnsureStatus(EnsureStatus.CLIAMREFUSE.getValue());
        return userConcurRelationDAO.update(userConcurRelationDO);
    }

    @Override
    public boolean claimUserRelation(long id) {
        UserConcurRelationDO userConcurRelationDO= selectUserConcurRelation(id);
        userConcurRelationDO.setEnsureStatus(EnsureStatus.CLIAM.getValue());
        return userConcurRelationDAO.update(userConcurRelationDO);
    }

    @Override
    public boolean setStatusNormally(long id) {
        return userConcurRelationDAO.setStatusNormally(id);
    }

    @Override
    public UserConcurRelationDO selectUserConcurRelation(long id) {
        return (UserConcurRelationDO)userConcurRelationDAO.selectById(id);
    }

    @Override
    public List<UserConcurRelationDO> selectUserConcurRelationByIdS(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }
        UserConcurRelationQueryDO queryDO = new UserConcurRelationQueryDO();
        queryDO.setIds(ids);
        queryDO.setPageSize(ids.size());
        return userConcurRelationDAO.selectByQuery(queryDO);
    }

    @Override
    public Long statisticsRatio(long concurId, String startJoinDate) {
        UserConcurRelationQueryDO queryDO = new UserConcurRelationQueryDO();
        queryDO.setConcurPlanId(concurId);
        queryDO.setEndJoinTime(startJoinDate);
        queryDO.setEnsureStatusList(NORMALLYSTATUS);
        return userConcurRelationDAO.statisticsRatio(queryDO);
    }


    public synchronized void init() {
        cycleLifeManager.addCycleLife(this);
        List<ConcurPlanDO> list = concurPlanDAO.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            logger.warn("not found concurplan");
            return;
        }
        for (ConcurPlanDO concurPlanDO : list) {
            cacheConcurMap.put(concurPlanDO.getId(), concurPlanDO);
            ConcurStatistics concurStatistics = new ConcurStatistics();
            concurStatistics.setConcurPlanId(concurPlanDO.getId());

        }
    }

    public synchronized void destroy() {

    }

    @Override
    public void reInit() {
        clean();
    }

    @Override
    public void stop() {

    }



}
