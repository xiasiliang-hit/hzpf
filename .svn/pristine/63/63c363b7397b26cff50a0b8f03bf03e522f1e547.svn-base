package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserAppOpenIdDO;
import com.line.bqxd.platform.client.dataobject.query.UserAppOpenIdQueryDO;
import com.line.bqxd.platform.dao.UserAppOpenIdDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserAppOpenIdDAOTest {

    @Resource  //自动注入,默认按名称
    private UserAppOpenIdDAO<UserAppOpenIdDO> userAppOpenIdDAO;

    @Test
    public void testInsert(){
        UserAppOpenIdDO userAppOpenIdDO = new UserAppOpenIdDO();
        userAppOpenIdDO.setUserId(1234);
        userAppOpenIdDO.setOpenId(System.currentTimeMillis()+"openid");
        userAppOpenIdDO.setPfAppId(100);
        userAppOpenIdDO.setUnionId(System.currentTimeMillis()+"unionid");
        long r=userAppOpenIdDAO.insert(userAppOpenIdDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        /*
        ShareDO concurPlanDO = new ShareDO();
        concurPlanDO.setId(1);

        concurPlanDO.setActivityId(2);
        concurPlanDO.setShareUserId(2);
        concurPlanDO.setChannelId(1);
        concurPlanDO.setShareTextId(1);

        concurPlanDO.setLevel(4);

        boolean r=articleDAO.update(concurPlanDO);
        Assert.assertTrue(r);
        */
    }
    @Test
    public void testSelectById(){
        long id=1;
        UserAppOpenIdDO concurPlanDO=(UserAppOpenIdDO)userAppOpenIdDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }

    @Test
    public void testDeleteById(){

    }

    @Test
    public void testQueryByUnionIdAndAppId(){
        UserAppOpenIdQueryDO queryDO = new UserAppOpenIdQueryDO();
        queryDO.setUnionId("1469612154255unionid");
        queryDO.setPfAppId(100);

        List<UserAppOpenIdDO> list= userAppOpenIdDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }


    @Test
    public void testQueryByOpenId(){
        UserAppOpenIdQueryDO queryDO = new UserAppOpenIdQueryDO();
        queryDO.setOpenId("1469612154255openid");

        List<UserAppOpenIdDO> list= userAppOpenIdDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }

}
