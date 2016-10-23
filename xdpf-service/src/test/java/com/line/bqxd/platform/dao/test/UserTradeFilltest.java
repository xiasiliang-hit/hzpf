package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.UserTradeFillDAO;
import com.line.bqxd.platform.dao.UserTradePayDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserTradeFilltest {
    @Resource  //自动注入,默认按名称
    private UserTradeFillDAO userTradeFillDAO;

    @Test
    public void testInsert(){
        UserTradeFillDO claimDO = new UserTradeFillDO();
        claimDO.setUserId(3);
        claimDO.setAttach("test");
        claimDO.setTradeId("testTradeId");
        claimDO.setTotalFee(120);
        claimDO.setOpenId("openid");
        claimDO.setBankType("bankType");
        claimDO.setCashFee(12);
        claimDO.setExplains("explain");
        claimDO.setCashFeeType("cashfeetype");
        claimDO.setErrCode("123");
        claimDO.setErrCodeDes("errordes");
        claimDO.setFeeType("feetype");
        claimDO.setPayStatus(1);
        claimDO.setPayType("34");
        claimDO.setTimeStart("timestart");
        claimDO.setTimeEnd("timeend");
        claimDO.setTimeExpire("timeexpire");
        claimDO.setTradeId("tradeid");
        claimDO.setTradeStatus(3);
        claimDO.setTransactionId("tridddd");
        claimDO.setWxTradeType("wxjs");
        long r=userTradeFillDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        UserTradeFillDO claimDO = new UserTradeFillDO();
        claimDO.setId(1);

        claimDO.setAttach("testupdate");
        claimDO.setTradeId("testTradeIdupdate");
        claimDO.setTotalFee(1200);
        claimDO.setOpenId("openidupdate");
        claimDO.setBankType("bankTypeupdate");
        claimDO.setExplains("explainupdate");
        claimDO.setCashFeeType("cashfeetypeupdate");
        claimDO.setErrCode("123update");
        claimDO.setErrCodeDes("errordesupdate");
        claimDO.setFeeType("feetypeupdate");
        claimDO.setPayStatus(2);
        claimDO.setPayType("34update");
        claimDO.setTimeStart("timestartupdate");
        claimDO.setTimeEnd("timeendupdate");
        claimDO.setTimeExpire("timeexpireupdate");
        claimDO.setTradeId("tradeidupdate");
        claimDO.setTradeStatus(6);
        claimDO.setWxTradeType("wxjsupdate");

        boolean r=userTradeFillDAO.update(claimDO);
        Assert.assertTrue(r);
    }

    @Test
    public void testUpdateByTransactionId(){
        UserTradeFillDO claimDO = new UserTradeFillDO();
        claimDO.setAttach("testupdate");
        claimDO.setTradeId("testTradeIdupdate");
        claimDO.setTotalFee(1200);
        claimDO.setOpenId("openidupdate");
        claimDO.setBankType("bankTypeupdate");
        claimDO.setExplains("explainupdate");
        claimDO.setCashFeeType("cashfeetypeupdate");
        claimDO.setErrCode("123update");
        claimDO.setErrCodeDes("errordesupdate123");
        claimDO.setFeeType("feetypeupdate");
        claimDO.setPayStatus(2);
        claimDO.setPayType("34update");
        claimDO.setTimeStart("timestartupdate");
        claimDO.setTimeEnd("timeendupdate");
        claimDO.setTimeExpire("timeexpireupdate");
        claimDO.setTradeId("tradeidupdate");
        claimDO.setTradeStatus(6);
        claimDO.setWxTradeType("wxjsupdate");
        claimDO.setTransactionId("trid");
        boolean r=userTradeFillDAO.updateByTransactionId(claimDO);
        Assert.assertTrue(r);
    }

    @Test
    public void testSelectByQuery(){
        UserTradeFillQueryDO userClaimApplyQueryDO = new UserTradeFillQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("tradeid");

        List<UserTradePayDO> list=userTradeFillDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        UserTradeFillQueryDO userClaimApplyQueryDO = new UserTradeFillQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("tradeid");

        long r=userTradeFillDAO.countByQuery(userClaimApplyQueryDO);


        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        UserTradeFillDO claimApplyDO=(UserTradeFillDO) userTradeFillDAO.selectById(2);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==2);
    }
}
