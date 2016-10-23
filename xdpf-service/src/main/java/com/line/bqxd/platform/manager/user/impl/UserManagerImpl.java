package com.line.bqxd.platform.manager.user.impl;

import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserAppOpenIdDO;
import com.line.bqxd.platform.client.dataobject.UserBalanceDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserAppOpenIdQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserBalanceQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.dao.UserAppOpenIdDAO;
import com.line.bqxd.platform.dao.UserBalanceDAO;
import com.line.bqxd.platform.dao.UserDAO;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.line.bqxd.platform.client.constant.Status.UserStatus;

/**
 * Created by huangjianfei on 16/5/16.
 */
public class UserManagerImpl implements UserManager {
    private static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    @Resource
    private UserDAO userDAO;

    @Resource
    private UserAppOpenIdDAO<UserAppOpenIdDO> userAppOpenIdDAO;

    @Resource
    private UserBalanceDAO<UserBalanceDO> userBalanceDAO;

    @Resource
    private ConcurManager concurManager;

    @Override
    public void updateWXByOpenId(UserDO userDO, boolean isUpdateGroupHead) {
        //更新用户相关微信数据
        userDAO.updateWXByOpenid(userDO);

    }

    @Override
    public void updateWXByUnionId(UserDO userDO) {
        userDAO.updateWXByUnionid(userDO);
    }

    @Override
    public long insert(UserDO userDO) {
        if (userDO == null) {
            throw new NullPointerException("userDO null");
        }


        if (userDO.getStatus() == 0) {
            userDO.setStatus(1);
        }
        return  userDAO.insert(userDO);
    }

    @Override
    public long insert(UserDO userDO, long pfAppId) {
        long userId = 0;
        //如果已经存在,则进行修改
        UserDO exsitUserDO = selectByUnionId(userDO.getUnionid());
        if (exsitUserDO == null) {
            userId = insert(userDO);
        } else {
            updateWXByUnionId(userDO);
            userId = exsitUserDO.getUserId();
        }
        UserAppOpenIdDO userAppOpenIdDO = new UserAppOpenIdDO();
        userAppOpenIdDO.setUnionId(userDO.getUnionid());
        userAppOpenIdDO.setOpenId(userDO.getOpenid());
        userAppOpenIdDO.setUserId(userId);
        userAppOpenIdDO.setPfAppId(pfAppId);
        userAppOpenIdDAO.insert(userAppOpenIdDO);
        return userId;
    }

    @Override
    public void perfectUser(long userId, String card, String userName, String mobile) {
        if (StringUtils.isBlank(card) && StringUtils.isBlank(userName) && StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("perfectUser argument eror, card=" + card + ",userName=" + userName + ",mobile=" + mobile);
        }
        UserDO userDO = userDAO.selectByUserId(userId);
        if (userDO != null) {
            userDO.setCard(card);
            if (StringUtils.isNotBlank(card)) {
                userDO.setBirth(BizUtils.getBirth(card));
                userDO.setSex(BizUtils.getSex(card));
            }
            userDO.setMobile(mobile);
            userDO.setUserName(userName);
            userDAO.update(userDO);
        }
    }

    @Override
    public void perfectUserBank(long userId, String bankNo, String bankName) {
        if (StringUtils.isBlank(bankNo) || StringUtils.isBlank(bankName) || userId<=0) {
            throw new IllegalArgumentException("perfectUserBank argument eror, card=" + bankNo + ",bankName=" + bankName + ",userId=" + userId);
        }
        UserDO userDO = userDAO.selectByUserId(userId);
        if (userDO != null) {
            userDO.setBankNo(bankNo);
            userDO.setBankName(bankName);
            userDAO.update(userDO);
        }
    }

    @Override
    public UserDO selectByUnionId(String unionId) {
        return userDAO.selectByUnionId(unionId);
    }

    @Override
    public UserDO selectByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            logger.warn("UserManager selectByOpenId openId is blank");
            return null;
        }

        return userDAO.selectByOpenId(openId);
    }

    @Override
    public UserDO selectByOpenId(String openId, long pfAppid) {
        if (StringUtils.isBlank(openId) || pfAppid<=0) {
            logger.warn("UserManager selectByOpenId parameter is blank,openId={},appid={}", openId, pfAppid);
            return null;
        }

        String unionId = getUnionId(openId, pfAppid);
        if (StringUtils.isBlank(unionId)) {
            return null;
        } else {
            UserDO userDO = selectByUnionId(unionId);
            if(userDO!=null) {
                userDO.setOpenid(openId);
            }
            return userDO;
        }
    }

    @Override
    public UserDO selectByUserId(long userId) {
        if (userId <= 0) {
            logger.warn("UserManager selectByUserId Illegal userId={}", userId);
            return null;
        }
        return userDAO.selectByUserId(userId);
    }

    @Override
    public void delUser(long userId) {
        if (userId <= 0) {
            logger.warn("UserManager delUser Illegal userId={}", userId);
        }
        UserDO userDO = new UserDO();
        userDO.setUserId(userId);
        userDO.setStatus(UserStatus.DEL.getValue());
        userDAO.update(userDO);
    }

    @Override
    public void recoverUser(long userId) {
        if (userId <= 0) {
            logger.warn("UserManager recoverUser Illegal userId={}", userId);
        }
        UserDO userDO = new UserDO();
        userDO.setUserId(userId);
        userDO.setStatus(UserStatus.NORMALLY.getValue());
        userDAO.update(userDO);
    }




    @Override
    public UserDO selectByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("selectByMobile argument eror, mobile null");
        }
        UserQueryDO userQueryDO = new UserQueryDO();
        userQueryDO.setMobile(mobile);
        List<UserDO> list = userDAO.selectByQueryPage(userQueryDO);
        return (list != null && list.size() > 0) ? list.get(0) : null;
    }

    @Override
    public UserDO selectByCard(String card) {
        if (StringUtils.isBlank(card)) {
            throw new IllegalArgumentException("selectByCard argument eror, card null");
        }
        UserQueryDO userQueryDO = new UserQueryDO();
        userQueryDO.setCard(card);
        List<UserDO> list = userDAO.selectByQueryPage(userQueryDO);
        return (list != null && list.size() > 0) ? list.get(0) : null;
    }

    @Override
    public List<UserDO> selectByIdList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        UserQueryDO userQueryDO = new UserQueryDO();
        userQueryDO.setIdList(ids);
        userQueryDO.setPageSize(ids.size());
        return userDAO.selectByQueryPage(userQueryDO);

    }

    @Override
    public List<UserDO> selectByQueryPage(UserQueryDO userQueryDO) {
        if (userQueryDO == null) {
            userQueryDO = new UserQueryDO();
            userQueryDO.initStartNum();
        }
        return userDAO.selectByQueryPage(userQueryDO);
    }

    @Override
    public Long countByQueryPage(UserQueryDO userQueryDO) {
        if (userQueryDO == null) {
            userQueryDO = new UserQueryDO();
        }
        return userDAO.countByQueryPage(userQueryDO);
    }

    @Override
    public Map<Long, UserDO> selectByUserIds(List<Long> ids) {
        List<UserDO> list = selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<Long, UserDO> map = new HashMap<Long, UserDO>();
        for (UserDO user : list) {
            map.put(user.getUserId(), user);
        }
        return map;
    }

    @Override
    public boolean updateByOpenid(UserDO userDO) {
        return userDAO.updateByOpenid(userDO);
    }

    @Override
    public void updateMemberFee(String openId, long fee) {
        UserDO userDO = new UserDO();
        userDO.setOpenid(openId);

        boolean result=updateByOpenid(userDO);
        if(result){
            if(logger.isDebugEnabled()) {
                logger.debug("updateMemberFee is success,openId={},fee={}",openId,fee);
            }
        }else{
            logger.warn("updateMemberFee is fail,openId={},fee={}",openId,fee);
        }
    }

    @Override
    public boolean addBalance(long userId, long concurId, long fee) {
        return updateBalance(userId,concurId,fee,true);
    }

    @Override
    public boolean reduceBalance(long userId, long concurId, long fee) {
        return updateBalance(userId,concurId,fee,false);
    }

    @Override
    public long getBalance(long userId, long concurId) {
        UserBalanceQueryDO queryDO = new UserBalanceQueryDO();
        queryDO.setUserId(userId);
        queryDO.setConcurId(concurId);
        List<UserBalanceDO> list = userBalanceDAO.selectByQuery(queryDO);
        if(CollectionUtils.isEmpty(list)) {
            return 0;
        }
        long balance=0;
        for(UserBalanceDO userBalanceDO:list){
            balance+=userBalanceDO.getBalance();
        }
        return balance;
    }

    private boolean updateBalance(long userId, long concurId, long fee,boolean isAdd){
        UserBalanceQueryDO queryDO = new UserBalanceQueryDO();
        queryDO.setUserId(userId);
        queryDO.setConcurId(concurId);
        List<UserBalanceDO> list = userBalanceDAO.selectByQuery(queryDO);
        if (CollectionUtils.isEmpty(list)) {
            if (!isAdd) {
                throw new IllegalArgumentException("updateBalance argumnet fail,balance is zero,concurId=" + concurId + " not exsit");
            }
            ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurId);
            if (concurPlanDO != null) {

                UserBalanceDO userBalanceDO = new UserBalanceDO();
                userBalanceDO.setBalance(fee);
                userBalanceDO.setConcurId(concurId);
                userBalanceDO.setPfAppId(concurPlanDO.getPfAppId());
                userBalanceDO.setUserId(userId);
                long rt = userBalanceDAO.insert(userBalanceDO);
                return rt > 0 ? true : false;
            } else {
                throw new IllegalArgumentException("updateBalance argumnet fail,concurId=" + concurId + " not exsit");
            }
        } else {
            UserBalanceDO userBalanceDO = list.get(0);
            if(isAdd){
                userBalanceDO.setBalance(userBalanceDO.getBalance() + fee);
            }else {
                userBalanceDO.setBalance(userBalanceDO.getBalance() - fee);
            }
            return userBalanceDAO.update(userBalanceDO);
        }
    }

    @Override
    public boolean delete(long userId) {
        return userDAO.delete(userId);
    }

    @Override
    public String getOpenId(long pfAppid, long userId) {
        UserAppOpenIdQueryDO userAppOpenIdQueryDO =new UserAppOpenIdQueryDO();
        userAppOpenIdQueryDO.setUserId(userId);
        userAppOpenIdQueryDO.setPfAppId(pfAppid);
        List<UserAppOpenIdDO> list=getList(userAppOpenIdQueryDO);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0).getOpenId();

    }


    public String getUnionId(String openId, long pfAppid){
        UserAppOpenIdQueryDO userAppOpenIdQueryDO =new UserAppOpenIdQueryDO();
        userAppOpenIdQueryDO.setOpenId(openId);
        userAppOpenIdQueryDO.setPfAppId(pfAppid);
        List<UserAppOpenIdDO> list=getList(userAppOpenIdQueryDO);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0).getUnionId();
    }

    @Override
    public List<UserAppOpenIdDO> getList(UserAppOpenIdQueryDO userAppOpenIdQueryDO){
        return userAppOpenIdDAO.selectByQuery(userAppOpenIdQueryDO);
    }

}
