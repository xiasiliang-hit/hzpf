package com.line.bqxd.platform.manager.user.impl;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.BillListType;
import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.dao.*;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wx.TemplateMsgManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXOrderResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayBaseResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayNotifyRequest;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/7/8.
 */
public class UserPayManagerImpl implements UserPayManager {

    private static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    @Resource
    private UserDAO userDAO;

    @Resource
    private UserTradePayDAO userTradePayDAO;

    @Resource
    private UserTradeBillDAO userTradeBillDAO;

    @Resource
    private UserTradeCashDAO userTradeCashDAO;

    @Resource
    private UserTradeFillDAO userTradeFillDAO;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @Override
    public boolean fillUserCash(long userId,long concurId ,long fillFee) {
        return userManager.addBalance(userId,concurId,fillFee);
    }

    @Override
    public boolean getUserCash(long userId, long concurId,long fee) {
        //需要变成负数,因为里面都是通过加来实现的
        return userManager.reduceBalance(userId, concurId,fee);
    }

    @Override
    public long getUserBalance(long userId) {
        UserDO userDO = userDAO.selectByUserId(userId);
        if (userDO == null) {
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    public long getUserPayCash(long userId) {
        return userTradePayDAO.sumFeeByUserId(userId);
    }

    @Override
    public boolean insertUserTradePayDO(UserTradePayDO userTradePayDO) {
        long rt = userTradePayDAO.insert(userTradePayDO);
        if (rt > 0) {
            UserTradeBillDO userTradeBillDO = new UserTradeBillDO();
            userTradeBillDO.setExplains(userTradePayDO.getExplains());
            userTradeBillDO.setFee(userTradePayDO.getFee());
            userTradeBillDO.setTradeId(userTradePayDO.getTradeId());
            userTradeBillDO.setUserId(userTradePayDO.getUserId());
            userTradeBillDO.setAttach(userTradePayDO.getAttach());
            userTradeBillDO.setListType(BillListType.PAY.getValue());

            try {
                if (insertUserTradeBillDO(userTradeBillDO)) {
                    return true;
                } else {
                    logger.error("user insert trade bill fail, {}", userTradeBillDO);
                    return true;
                }
            } catch (Exception e) {
                logger.error("user insert trade bill error," + userTradeBillDO, e);
                return true;
            }
        } else {
            logger.error("user insert trade pay fail, {}", userTradePayDO);

        }

        return false;
    }

    @Override
    public boolean insertUserTradeCashDO(UserTradeCashDO userTradeCashDO) {
        long rt = userTradeCashDAO.insert(userTradeCashDO);
        if (rt > 0) {
            UserTradeBillDO userTradeBillDO = new UserTradeBillDO();
            userTradeBillDO.setExplains(userTradeCashDO.getExplains());
            userTradeBillDO.setFee(userTradeCashDO.getTotalFee());
            userTradeBillDO.setTradeId(userTradeCashDO.getTradeId());
            userTradeBillDO.setUserId(userTradeCashDO.getUserId());
            userTradeBillDO.setAttach("体现");
            userTradeBillDO.setListType(BillListType.CASH.getValue());
            try {
                if (insertUserTradeBillDO(userTradeBillDO)) {
                    return true;
                } else {
                    logger.error("insertUserTradeCashDO user insert trade bill fail, {}", userTradeBillDO);
                    return true;
                }
            } catch (Exception e) {
                logger.error("insertUserTradeCashDO user insert trade bill error," + userTradeBillDO, e);
                return true;
            }
        } else {
            logger.error("user insert trade cash fail, {}", userTradeCashDO);

        }
        return false;
    }

    @Override
    public boolean insertUserTradeFillDOAndSetId(UserTradeFillDO userTradeFillDO) {
        long rt = userTradeFillDAO.insert(userTradeFillDO);
        userTradeFillDO.setId(rt);
        return rt > 0 ? true : false;
    }

    @Override
    public boolean userTradeFillSuccess(UserTradeFillDO userTradeFillDO, WXPayResult wxPayResult) {
        boolean result = false;
        UserTradeFillDO update = new UserTradeFillDO();
        update.setId(userTradeFillDO.getId());
        update.setPayStatus(Status.Pay.PAYSUBMIT.getValue());
        update.setTradeStatus(Status.Trade.HANDLE.getValue());
        update.setErrCode(wxPayResult.getErrCode());
        update.setErrCodeDes(wxPayResult.getErrCodeDes());
        update.setPrepayId(wxPayResult.getPrepayId());
        try {
            result = userTradeFillDAO.update(update);
            if (result) {

                logger.warn("userTradeFillSuccess is success,{},{}", userTradeFillDO, wxPayResult);
                result = true;

            } else {
                logger.error("userTradeFillSuccess method execute userTradeFillDAO.update fail,{},{},{}", userTradeFillDO, wxPayResult, update);
            }
        } catch (Exception e) {
            logger.error("userTradeFillSuccess method execute userTradeFillDAO.update error," + userTradeFillDO + "," + wxPayResult + "," + update, e);
        }
        return result;
    }

    @Override
    public boolean userTradeFillCallback(WXPayNotifyRequest wxPayNotifyRequest) {
        logger.info("userTradeFillCallback execute {}",wxPayNotifyRequest);
        BizFeeType bizFeeType=getBizFeeType(wxPayNotifyRequest.getAttach());
        String[] ids=getids(wxPayNotifyRequest.getAttach());
        boolean result= concurManager.setEnsureNormally(ids);
        if(result){
            result= userTradeFillCashHandle(wxPayNotifyRequest.getOutTradeNo(), wxPayNotifyRequest.getTransactionId(), DateUtils.getDateTimeFormat(), Long.parseLong(wxPayNotifyRequest.getTotalFee()),bizFeeType==BizFeeType.CONCURFEE?true:false);
            if(result){
                if(logger.isDebugEnabled()){
                    logger.debug("userTradeFillCallback success,transactionId={}",wxPayNotifyRequest.getTransactionId());
                }
            }else{
                logger.warn("userTradeFillCashHandle fail,but set ensure normally success, {}",wxPayNotifyRequest);
            }
        }else{
            logger.warn("setEnsureNormally is fail,ids={}",ids);
        }

        return result;
    }

    @Override
    public boolean userTradeFillCollate(WXOrderResult wxOrderResult) {
        logger.info("userTradeFillCollate execute {}",wxOrderResult);
        BizFeeType bizFeeType=getBizFeeType(wxOrderResult.getAttach());
        String[] ids=getids(wxOrderResult.getAttach());

        //处理更新用户会员费用
        if(bizFeeType==BizFeeType.MEMBERFEE||bizFeeType==BizFeeType.CONCURFEE) {
            userManager.updateMemberFee(wxOrderResult.getOpenId(),Long.parseLong(wxOrderResult.getTotalFee()));
        }

        boolean reslut= userTradeFillCashHandle(wxOrderResult.getOutTradeNo(), wxOrderResult.getTransactionId(), wxOrderResult.getTimeEnd(), Long.parseLong(wxOrderResult.getTotalFee()),bizFeeType==BizFeeType.CONCURFEE?true:false);
        if(reslut) {
            concurManager.setEnsureNormally(ids);
        }
        return reslut;
    }

    private boolean userTradeFillCashHandle(String outTradeNo,String transactionId,String endDate,long totalFee,boolean isUpdateBalance){
        UserTradeFillDO userTradeFillDO = selectByTradeId(outTradeNo);
        if (userTradeFillDO == null) {
            logger.warn("userTradeFillCallback execute fill cash fail,userTradeFillDO is null,outTradeNo={},transactionId={}", outTradeNo,transactionId);
            return false;
        }
        boolean result = false;
        UserTradeFillDO update = new UserTradeFillDO();
        //update.setTradeId(userTradeFillDO.getTradeId());
        update.setId(userTradeFillDO.getId());
        update.setPayStatus(Status.Pay.PAYSUCCESS.getValue());
        update.setTradeStatus(Status.Trade.OVEN.getValue());
        //update.setTimeEnd(DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT2,endDate));
        update.setTimeEnd(endDate);

        update.setTransactionId(transactionId);
        try {
            result = userTradeFillDAO.update(update);

            if (result) {
                logger.warn("userTradeFillCashHandle execute fill cash record success,outTradeNo={}", outTradeNo);
                //判断是否需要更新用户的余额
                boolean insertBillFlag = false;
                if (!isUpdateBalance) {
                    insertBillFlag = true;
                } else if (fillUserCash(userTradeFillDO.getUserId(),userTradeFillDO.getConcurId(), totalFee)) {
                    insertBillFlag = true;
                }
                if (insertBillFlag) {

                    UserTradeBillDO userTradeBillDO = new UserTradeBillDO();
                    userTradeBillDO.setExplains(userTradeFillDO.getExplains());
                    userTradeBillDO.setFee(userTradeFillDO.getTotalFee());
                    userTradeBillDO.setTradeId(userTradeFillDO.getTradeId());
                    userTradeBillDO.setUserId(userTradeFillDO.getUserId());
                    userTradeBillDO.setAttach(userTradeFillDO.getAttach());
                    userTradeBillDO.setListType(BillListType.FILL.getValue());
                    userTradeBillDO.setConcurId(userTradeFillDO.getConcurId());

                    if (insertUserTradeBillDO(userTradeBillDO)) {
                        logger.warn("userTradeFillCashHandle userTradeFillSuccess is success,{}", userTradeFillDO);
                    } else {
                        logger.warn("userTradeFillCashHandle userTradeFillSuccess is success,{}", userTradeFillDO);
                    }

                    logger.warn("userTradeFillCashHandle execute update user balance success,userId={},outTradeNo={},fee={}", userTradeFillDO.getUserId(), outTradeNo, totalFee);

                } else {
                    logger.warn("userTradeFillCashHandle execute update user balance fail,userId={},outTradeNo={},fee={}", userTradeFillDO.getUserId(), outTradeNo, totalFee);
                }

            } else {
                logger.warn("userTradeFillCashHandle execute fill cash record fail,outTradeNo={}", outTradeNo);
            }
        } catch (Exception e) {
            logger.error("userTradeFillCashHandle execute error,outTradeNo=" + outTradeNo, e);
        }
        return result;
    }

    @Override
    public boolean userTradeFillFail(UserTradeFillDO userTradeFillDO, WXPayBaseResult wxPayBaseResult) {
        boolean result = false;
        UserTradeFillDO update = new UserTradeFillDO();
        update.setId(userTradeFillDO.getId());
        if (wxPayBaseResult instanceof WXOrderResult) {
            if (((WXOrderResult) wxPayBaseResult).getTradeState().equalsIgnoreCase(Status.WXPay.NOTPAY.name())) {
                update.setPayStatus(Status.Pay.NOTPAY.getValue());
                update.setErrCode(Status.WXPay.NOTPAY.name());
                update.setErrCodeDes("未支付");
            }
        } else {
            update.setPayStatus(Status.Pay.PAYFAIL.getValue());
            if (StringUtils.isNotBlank(wxPayBaseResult.getErrCode())) {
                update.setErrCode(wxPayBaseResult.getErrCode());
            } else {
                update.setErrCode(wxPayBaseResult.getReturnCode());
            }
            if (StringUtils.isNotBlank(wxPayBaseResult.getErrCodeDes())) {
                update.setErrCodeDes(wxPayBaseResult.getErrCodeDes());
            } else {
                update.setErrCodeDes(wxPayBaseResult.getReturnMsg());
            }
        }
        update.setTradeStatus(Status.Trade.OVEN.getValue());

        if(wxPayBaseResult instanceof WXPayResult)
            if (StringUtils.isNotBlank(((WXPayResult) wxPayBaseResult).getPrepayId())) {
                update.setPrepayId(((WXPayResult) wxPayBaseResult).getPrepayId());
            }
        try {
            result = userTradeFillDAO.update(update);
            if (result) {
                logger.warn("userTradeFillFail execute success," + userTradeFillDO + "," + wxPayBaseResult + "," + update);
            } else {
                logger.warn("userTradeFillFail execute fail," + userTradeFillDO + "," + wxPayBaseResult + "," + update);
            }
        } catch (Exception e) {
            logger.error("userTradeFillFail execute error," + userTradeFillDO + "," + wxPayBaseResult + "," + update, e);
        }
        return result;
    }

    @Override
    public boolean insertUserTradeBillDO(UserTradeBillDO userTradeBillDO) {
        long rt = userTradeBillDAO.insert(userTradeBillDO);
        return rt > 0 ? true : false;
    }

    @Override
    public List<UserTradeBillDO> getTradeBillListByPage(UserTradeBillQueryDO queryDO) {
        if (queryDO == null) {
            queryDO = new UserTradeBillQueryDO();
            queryDO.initStartNum();
        }

        return userTradeBillDAO.selectByQuery(queryDO);
    }

    @Override
    public Long countByQuery(UserTradeBillQueryDO queryDO) {
        if (queryDO == null) {
            queryDO = new UserTradeBillQueryDO();
        }
        return userTradeBillDAO.countByQuery(queryDO);
    }

    @Override
    public UserTradeFillDO selectByTransactionId(String transactionId) {
        return userTradeFillDAO.selectByTransactionId(transactionId);
    }

    @Override
    public UserTradeFillDO selectByTradeId(String tradeId) {
        if (StringUtils.isBlank(tradeId)) {
            logger.warn("selectByTradeId tradeId is null");
            return null;
        }
        UserTradeFillQueryDO queryDO = new UserTradeFillQueryDO();
        queryDO.setTradeId(tradeId);
        queryDO.initStartNum();

        List<UserTradeFillDO> list = userTradeFillDAO.selectByQuery(queryDO);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;

    }

    @Override
    public List<UserTradeFillDO> selectTradeFillByQuery(UserTradeFillQueryDO userTradeFillQueryDO) {
        if(userTradeFillQueryDO==null){
            userTradeFillQueryDO.initStartNum();
        }
        return userTradeFillDAO.selectByQuery(userTradeFillQueryDO);
    }

    @Override
    public List<UserTradeFillDO> selectUnusualTradeFillByDay(String day) throws Exception {
        Date startdate = DateUtils.parse(day + "000001", DateUtils.DATA_TIME_FORMAT2);
        Date enddate = DateUtils.parse(day + "235959", DateUtils.DATA_TIME_FORMAT2);
        UserTradeFillQueryDO userTradeFillQueryDO = new UserTradeFillQueryDO();
        userTradeFillQueryDO.setPayStatus(Status.Pay.PAYSUBMIT.getValue());
        userTradeFillQueryDO.setStartDate(startdate);
        userTradeFillQueryDO.setEndDate(enddate);
        //设置比较大,少了一次查询
        userTradeFillQueryDO.setPageSize(1000000);
        return selectTradeFillByQuery(userTradeFillQueryDO);
    }


    private BizFeeType getBizFeeType(String attach){
        if (StringUtils.isBlank(attach)) {
            return null;
        }
        Map<String, String> map = (Map) JSON.parse(attach);
        if (map != null) {
            String bizFeeType = map.get("bizFeeType");
            if (BizFeeType.CONCURFEE.getValue().equalsIgnoreCase(bizFeeType)) {
                return BizFeeType.CONCURFEE;
            } else if (BizFeeType.MEMBERFEE.getValue().equalsIgnoreCase(bizFeeType)) {
                return BizFeeType.MEMBERFEE;
            }
        }
        return null;

    }

    private String[] getids(String attach){
        if (StringUtils.isBlank(attach)) {
            return null;
        }
        Map<String, String> map = (Map) JSON.parse(attach);
        if (map != null) {
            String  ids = map.get("ids");
            if(StringUtils.isNotBlank(ids)){
                return ids.split(";");
            }

        }
        return null;

    }

}
