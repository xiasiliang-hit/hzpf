package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ClaimApplyDO;
import com.line.bqxd.platform.client.dataobject.ClaimDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.dao.UserClaimApplyDAO;
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
public class UserClaimApplyTest {
    @Resource  //自动注入,默认按名称
    private UserClaimApplyDAO<ClaimApplyDO> userClaimApplyDAO;

    @Test
    public void testInsert(){
        ClaimApplyDO claimDO = new ClaimApplyDO();
        claimDO.setConcurPlanId(1);
        claimDO.setUserConcurRelationId(3);
        claimDO.setAttach("12345");
        claimDO.setVerifyTime("20160909");
        claimDO.setVerifyTel("13777495730");
        claimDO.setVerifyUser("testUser");
        claimDO.setVerifyHospital("二保");
        claimDO.setVerifyEventId(1);
        claimDO.setDescr("简单描述");
        claimDO.setPicArray("testPicArray");
        claimDO.setFollowUserName("testFollowUser");
        claimDO.setFollowUserTel("1377749574834");
        claimDO.setAttach("testAttach");
        claimDO.setResult("refushTest");
        claimDO.setStatus(3);
        claimDO.setUserId(12);
        claimDO.setUserName("testUserName");
        claimDO.setCollectMoney(100);
        claimDO.setPreCollectMoney(50);
        claimDO.setBandyStartDay("day1");
        claimDO.setEventEndDay("day2");
        claimDO.setEventSubmitDay("day3");
        claimDO.setPublishStartDay("day4");
        claimDO.setMonthIndex(1);
        long r=userClaimApplyDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        ClaimApplyDO claimDO = new ClaimApplyDO();
        claimDO.setId(6);
        claimDO.setConcurPlanId(4);
        claimDO.setUserConcurRelationId(5);
        claimDO.setAttach("12345update");
        claimDO.setVerifyTime("20160909update");
        claimDO.setVerifyTel("13777495730update");
        claimDO.setVerifyUser("testUserupdate");
        claimDO.setVerifyHospital("二保update");
        claimDO.setVerifyEventId(2);
        claimDO.setPicArray("testPicArrayUpdate");
        claimDO.setDescr("简单描述update");
        claimDO.setFollowUserName("testFollowUserupdate");
        claimDO.setFollowUserTel("1377749574834update");
        claimDO.setAttach("testAttachupdate");
        claimDO.setResult("refushTestupdate");
        claimDO.setStatus(30);
        claimDO.setUserId(120);
        claimDO.setUserName("testUserNameupdate");
        claimDO.setCollectMoney(1000);
        claimDO.setPreCollectMoney(500);
        claimDO.setBandyStartDay("day1u");
        claimDO.setEventEndDay("day2u");
        claimDO.setEventSubmitDay("day3u");
        claimDO.setPublishStartDay("day4u");
        claimDO.setMonthIndex(10);
        boolean r=userClaimApplyDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        UserClaimApplyQueryDO userClaimApplyQueryDO = new UserClaimApplyQueryDO();
        userClaimApplyQueryDO.setUserId(12);
        userClaimApplyQueryDO.setUserName("testUserName");
        userClaimApplyQueryDO.setFollowUserName("testFollowUserupdate");
        userClaimApplyQueryDO.setStatus(30);
        List<ClaimApplyDO> list=userClaimApplyDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        UserClaimApplyQueryDO userClaimApplyQueryDO = new UserClaimApplyQueryDO();
        userClaimApplyQueryDO.setUserId(12);
        userClaimApplyQueryDO.setUserName("testUserName");
        userClaimApplyQueryDO.setFollowUserName("testFollowUserupdate");
        userClaimApplyQueryDO.setStatus(30);
        long r=userClaimApplyDAO.countByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        ClaimApplyDO claimApplyDO=(ClaimApplyDO) userClaimApplyDAO.selectById(3);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==3);
    }
}
