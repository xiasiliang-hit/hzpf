package com.line.bqxd.platform.manager.user;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.UserTradeBillDO;
import com.line.bqxd.platform.client.dataobject.UserTradeCashDO;
import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXOrderResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayBaseResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayNotifyRequest;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;

import java.util.List;

/**
 * Created by huangjianfei on 16/7/8.
 */
public interface UserPayManager {

    /**
     * 用户充值
     * @param userId
     * @param fillFee
     * @return
     */
    public boolean fillUserCash(long userId,long concurId ,long fillFee);

    /**
     * 用户体现
     * @param userId
     * @param fee
     * @return
     */
    public boolean getUserCash(long userId,long concurId,long fee);


    /**
     * 获取用户余额
     * @param userId
     * @return
     */
    public long getUserBalance(long userId);

    /**
     * 获取用户支付金额
     * @param userId
     * @return
     */
    public long getUserPayCash(long userId);

    /**
     * 插入用户扣款信息
     * @param userTradePayDO
     * @return
     */
    public boolean insertUserTradePayDO(UserTradePayDO userTradePayDO);

    /**
     * 插入用户体现记录
     * @param userTradeCashDO
     * @return
     */
    public boolean insertUserTradeCashDO(UserTradeCashDO userTradeCashDO);

    /**
     * 插入用户充值记录
     * @param userTradeFillDO
     * @return
     */
    public boolean insertUserTradeFillDOAndSetId(UserTradeFillDO userTradeFillDO);

    /**
     * 用户充值成功
     * @param userTradeFillDO
     * @param wxPayResult
     * @return
     */
    public boolean userTradeFillSuccess(UserTradeFillDO userTradeFillDO,WXPayResult wxPayResult);


    /**
     * 用户充值回调处理
     * @param wxPayNotifyRequest
     * @return
     */
    public boolean userTradeFillCallback(WXPayNotifyRequest wxPayNotifyRequest);

    /**
     * 用户兑账处理
     * @param wxOrderResult
     * @return
     */
    public boolean userTradeFillCollate(WXOrderResult wxOrderResult);

    /**
     * 用户充值失败
     * @param userTradeFillDO
     * @return
     */
    public boolean userTradeFillFail(UserTradeFillDO userTradeFillDO,WXPayBaseResult wxPayBaseResult);

    /**
     * 插入用户余额变动清单记录
      * @param userTradeBillDO
     * @return
     */
    public boolean insertUserTradeBillDO(UserTradeBillDO userTradeBillDO);


    public List<UserTradeBillDO> getTradeBillListByPage(UserTradeBillQueryDO queryDO);

    public Long countByQuery(UserTradeBillQueryDO queryDO);

    /**
     * 根据第三方支付ID获取订单记录
     * @param transactionId
     * @return
     */
    public UserTradeFillDO selectByTransactionId(String transactionId);


    /**
     * 根据自己业务系统交易ID获取交易记录
     * @param tradeId
     * @return
     */
    public UserTradeFillDO selectByTradeId(String tradeId);


    public List<UserTradeFillDO> selectTradeFillByQuery(UserTradeFillQueryDO userTradeFillQueryDO);

    /**
     * 查询具体一天异常的订单
     * @param day
     * @return
     */
    public List<UserTradeFillDO> selectUnusualTradeFillByDay(String day)throws Exception;
}
