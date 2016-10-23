package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.dao.UserConcurRelationDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserConcurRelationDAOTest {

    @Resource  //自动注入,默认按名称
    private UserConcurRelationDAO<UserConcurRelationDO> userConcurRelationDAO;

    @Test
    public void testInsert(){
        UserConcurRelationDO userConcurRelationDO = new UserConcurRelationDO();
        userConcurRelationDO.setUserType(1);
        userConcurRelationDO.setConcurPlanId(2);
        userConcurRelationDO.setFirstFillMoney(100);
        userConcurRelationDO.setUpperLimit(500);
        userConcurRelationDO.setEnsureStartTime("20160913");
        userConcurRelationDO.setRatio(100);
        userConcurRelationDO.setBirthDay("19810101");
        userConcurRelationDO.setChildWeek(31);
        userConcurRelationDO.setEnsureName("luohai");
        userConcurRelationDO.setEnsureStatus(1);
        userConcurRelationDO.setJoinTime("20160913");
        userConcurRelationDO.setWeight(3000);

        long r=userConcurRelationDAO.insert(userConcurRelationDO);

        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        UserConcurRelationDO userConcurRelationDO = new UserConcurRelationDO();
        userConcurRelationDO.setId(7);



        userConcurRelationDO.setEnsureStatus(9);
        userConcurRelationDO.setUserType(4);


        boolean r=userConcurRelationDAO.update(userConcurRelationDO);
        Assert.assertTrue(r);

    }
    @Test
    public void testSelectById(){
        UserConcurRelationDO userConcurRelationDO=(UserConcurRelationDO)userConcurRelationDAO.selectById(5);
        Assert.assertTrue(userConcurRelationDO!=null);
        Assert.assertTrue(userConcurRelationDO.getId()==5);
    }

    @Test
    public void testDeleteById(){
        long id=1;
        boolean r=userConcurRelationDAO.delete(id);
        Assert.assertTrue(r);
    }

    @Test
    public void testQuery(){
        UserConcurRelationQueryDO userConcurRelationQueryDO = new UserConcurRelationQueryDO();
        //userConcurRelationQueryDO.setUserId(24);
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(2);
        userConcurRelationQueryDO.setEnsureStatusList(statusList);
        userConcurRelationQueryDO.setConcurPlanId(2);

        List<UserConcurRelationDO> list=userConcurRelationDAO.selectByQuery(userConcurRelationQueryDO);
        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testQueryStatistics(){
        UserConcurRelationQueryDO userConcurRelationQueryDO = new UserConcurRelationQueryDO();
        userConcurRelationQueryDO.setConcurPlanId(2);
        Map<String,Long> map=userConcurRelationDAO.selectStatistics(userConcurRelationQueryDO);
        Assert.assertTrue(map!=null);
    }



}
