package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ClaimApplyDO;
import com.line.bqxd.platform.client.dataobject.UserTradeBillDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.UserTradeBillDAO;
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
public class UserTradePaytest {
    @Resource  //自动注入,默认按名称
    private UserTradePayDAO userTradePayDAO;

    @Test
    public void testInsert(){
        UserTradePayDO claimDO = new UserTradePayDO();
        claimDO.setUserId(3);
        claimDO.setAttach("test");
        claimDO.setExplains("explain");
        claimDO.setFee(34);
        claimDO.setTradeId("testTradeId");
        claimDO.setBizType("plan");
        claimDO.setOutBizId("123");
        claimDO.setSubUserId(123);
        claimDO.setSubUserName("SUBUSERNAME");
        long r=userTradePayDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        UserTradePayDO claimDO = new UserTradePayDO();
        claimDO.setId(1);
        claimDO.setFee(45);
        claimDO.setAttach("attachUpdate");
        claimDO.setExplains("explainsUpdate");

        boolean r=userTradePayDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        UserTradePayQueryDO userClaimApplyQueryDO = new UserTradePayQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        userClaimApplyQueryDO.setSubUserName("SUBUSERNAME");
        List<UserTradePayDO> list=userTradePayDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        UserTradePayQueryDO userClaimApplyQueryDO = new UserTradePayQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        userClaimApplyQueryDO.setSubUserName("SUBUSERNAME");
        int r=userTradePayDAO.countByQuery(userClaimApplyQueryDO);


        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        UserTradePayDO claimApplyDO=(UserTradePayDO) userTradePayDAO.selectById(2);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==2);
    }

    @Test
    public void testSumFeeByUserId(){
        long userId=3;
        long allFee=userTradePayDAO.sumFeeByUserId(userId);
        Assert.assertTrue(allFee>=0);
    }
}
