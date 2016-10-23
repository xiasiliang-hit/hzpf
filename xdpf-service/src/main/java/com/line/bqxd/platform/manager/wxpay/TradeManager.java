package com.line.bqxd.platform.manager.wxpay;

import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;

import java.util.List;

/**
 * Created by huangjianfei on 16/4/28.
 */
public interface TradeManager {



    /**
     * 用户充值
     * @param userId
     * @param totalFee
     * @param openId
     * @return
     */
    public BizResult<WXPayResult> fillCash(long userId, long concurId,long totalFee, String openId,String ip,String bizFeeType,String ids);


    /**
     * 用户体现接口
     * @param userId 用户ID
     * @param cashFee 体现额度
     * @return
     */
    public BizResult<Boolean> obtainCash(long userId,long cashFee);


    /**
     * 用户扣款接口
     * @param concurId
     * @param userId
     * @param payFee 扣款额度
     * @return
     */
    public BizResult<Boolean> payCash(long concurId, long userId, long payFee, UserDO subUserDO);


    /**
     * 分页条件查询
     * @param userTradeFillQueryDO
     * @return
     */
    public List<UserTradeFillDO> selectTradeFillByQueryPage(UserTradeFillQueryDO userTradeFillQueryDO);


    /**
     * 根据条件用户数据统计
     * @param userTradeFillQueryDO
     * @return
     */
    public Long countTradeFillByQueryPage(UserTradeFillQueryDO userTradeFillQueryDO);

}
