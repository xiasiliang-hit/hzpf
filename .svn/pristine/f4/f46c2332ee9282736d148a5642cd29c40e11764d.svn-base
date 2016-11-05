package com.line.bqxd.platform.manager.concur;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.common.DBBaseDO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by huangjianfei on 16/5/16.
 */
public class ConcurStatistics extends Base {
    //互助计划ID
    private long concurPlanId;

    //互助计划总用户数(包括子账户)
    private AtomicLong sumUserQuantity = new AtomicLong();

    //分摊系数
    private AtomicLong ratioSumQuantity = new AtomicLong();

    private AtomicLong moneyQuantity = new AtomicLong();

    public ConcurStatistics() {

    }

    public ConcurStatistics(long concurPlanId, AtomicLong sumUserQuantity, AtomicLong ratioSumQuantity) {
        this.concurPlanId = concurPlanId;
        this.sumUserQuantity = sumUserQuantity;
        this.ratioSumQuantity = ratioSumQuantity;

    }


    public long getConcurPlanId() {
        return concurPlanId;
    }

    public void setConcurPlanId(long concurPlanId) {
        this.concurPlanId = concurPlanId;
    }

    public AtomicLong getSumUserQuantity() {
        return sumUserQuantity;
    }

    public void setSumUserQuantity(AtomicLong sumUserQuantity) {
        this.sumUserQuantity = sumUserQuantity;
    }


}