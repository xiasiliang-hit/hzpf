package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.SmsDO;
import com.line.bqxd.platform.client.dataobject.UserBalanceDO;
import com.line.bqxd.platform.client.dataobject.query.UserBalanceQueryDO;
import com.line.bqxd.platform.dao.SmsDAO;
import com.line.bqxd.platform.dao.UserBalanceDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserBalanceDAOTest {

    @Resource
    private UserBalanceDAO userBalanceDAO;

    @Test
    public void testInsert(){
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setUserId(100);
        userBalanceDO.setPfAppId(200);
        userBalanceDO.setConcurId(20);
        userBalanceDO.setBalance(1000);
        long id=userBalanceDAO.insert(userBalanceDO);
        Assert.assertTrue(id>0);
    }

    @Test
    public void testUpdate(){

        UserBalanceDO userBalanceDO = new UserBalanceDO();

        userBalanceDO.setId(1);
        userBalanceDO.setBalance(1002);

        boolean rt=userBalanceDAO.update(userBalanceDO);
        Assert.assertTrue(rt);
    }

    @Test
    public void testQuery(){
        UserBalanceQueryDO queryDO = new UserBalanceQueryDO();
        queryDO.setConcurId(20);
        queryDO.setUserId(100);
        queryDO.setPfAppId(200);
        List<UserBalanceDO> list=userBalanceDAO.selectByQuery(queryDO);
        Assert.assertTrue(list!=null&&list.size()>0);
    }



}
