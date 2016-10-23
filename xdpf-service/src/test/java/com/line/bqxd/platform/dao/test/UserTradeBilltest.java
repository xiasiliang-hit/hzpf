package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ClaimApplyDO;
import com.line.bqxd.platform.client.dataobject.UserTradeBillDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.dao.UserClaimApplyDAO;
import com.line.bqxd.platform.dao.UserTradeBillDAO;
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
public class UserTradeBilltest {
    @Resource  //自动注入,默认按名称
    private UserTradeBillDAO<UserTradeBillDO> userTradeBillDAO;

    @Test
    public void testInsert(){
        UserTradeBillDO claimDO = new UserTradeBillDO();
        claimDO.setUserId(3);
        claimDO.setAttach("test");
        claimDO.setExplains("explain");
        claimDO.setFee(34);
        claimDO.setListType("pay");
        claimDO.setTradeId("testTradeId");
        long r=userTradeBillDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        UserTradeBillDO claimDO = new UserTradeBillDO();
        claimDO.setId(2);
        claimDO.setFee(45);
        claimDO.setAttach("attachUpdate");
        claimDO.setExplains("explainsUpdate");

        boolean r=userTradeBillDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        UserTradeBillQueryDO userClaimApplyQueryDO = new UserTradeBillQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        List<ClaimApplyDO> list=userTradeBillDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        UserTradeBillQueryDO userClaimApplyQueryDO = new UserTradeBillQueryDO();
        userClaimApplyQueryDO.setUserId(3);
        userClaimApplyQueryDO.setTradeId("testTradeId");
        long r=userTradeBillDAO.countByQuery(userClaimApplyQueryDO);


        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        UserTradeBillDO claimApplyDO=(UserTradeBillDO) userTradeBillDAO.selectById(2);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==2);
    }
}
