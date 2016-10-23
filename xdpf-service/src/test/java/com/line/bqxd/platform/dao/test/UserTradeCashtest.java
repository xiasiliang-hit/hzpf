package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserTradeCashDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeCashQueryDO;
import com.line.bqxd.platform.dao.UserTradeCashDAO;
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
public class UserTradeCashtest {
    @Resource  //自动注入,默认按名称
    private UserTradeCashDAO userTradeCashDAO;


    @Test
    public void testInsert(){
        UserTradeCashDO claimDO = new UserTradeCashDO();
        claimDO.setUserId(3);
        claimDO.setStatus(1);
        claimDO.setTradeId("testTradeId");
        claimDO.setExplains("explains");
        claimDO.setAccount("testAccount");
        claimDO.setChannel("wxpay");
        claimDO.setFeeToType(2);
        claimDO.setOpenId("testOpenId");
        claimDO.setProcedureFee(23);
        claimDO.setRefushCode("123");
        claimDO.setRefushMsg("2345");
        claimDO.setTotalFee(133);
        claimDO.setUserFee(45);
        claimDO.setExplains("explain");
        claimDO.setTradeId("testTradeId");

        long r=userTradeCashDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        UserTradeCashDO claimDO = new UserTradeCashDO();
        claimDO.setId(1);
        claimDO.setStatus(2);
        claimDO.setFeeToType(2);
        claimDO.setProcedureFee(236);
        claimDO.setRefushCode("123Update");
        claimDO.setRefushMsg("2345Update");
        claimDO.setTotalFee(1336);
        claimDO.setUserFee(456);
        claimDO.setExplains("explainUpdate");
        claimDO.setTradeId("testTradeIdUpdate");

        boolean r=userTradeCashDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        UserTradeCashQueryDO userClaimApplyQueryDO = new UserTradeCashQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        userClaimApplyQueryDO.setStatus(2);
        List<UserTradePayDO> list=userTradeCashDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    /*
    @Test
    public void testCountByQuery(){
        UserTradeCashQueryDO userClaimApplyQueryDO = new UserTradeCashQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        userClaimApplyQueryDO.setStatus(2);
        int r=userTradeCashDAO.countByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        UserTradeCashDO claimApplyDO=(UserTradeCashDO) userTradeCashDAO.selectById(1);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==1);
    }
    */
}
