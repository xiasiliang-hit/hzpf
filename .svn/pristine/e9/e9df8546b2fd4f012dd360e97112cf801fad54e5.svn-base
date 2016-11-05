package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.PfUserAdminDO;
import com.line.bqxd.platform.client.dataobject.UserAppOpenIdDO;
import com.line.bqxd.platform.client.dataobject.query.PfUserAdminQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserAppOpenIdQueryDO;
import com.line.bqxd.platform.dao.PfUserAdminDAO;
import com.line.bqxd.platform.dao.UserAppOpenIdDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class PfUserAdminDAOTest {

    @Resource  //自动注入,默认按名称
    private PfUserAdminDAO<PfUserAdminDO> pfUserAdminDAO;

    @Test
    public void testInsert(){
        PfUserAdminDO pfUserAdminDO = new PfUserAdminDO();
        pfUserAdminDO.setCorpId(123);
        pfUserAdminDO.setLastLoginIp("127.0.0.1");
        pfUserAdminDO.setLastLoginTime(new Date());
        pfUserAdminDO.setLoginName("test");
        pfUserAdminDO.setNickName("testNickName");
        pfUserAdminDO.setPassword("12345678");
        long r=pfUserAdminDAO.insert(pfUserAdminDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        PfUserAdminDO pfUserAdminDO = new PfUserAdminDO();
        pfUserAdminDO.setId(1);
        pfUserAdminDO.setNickName("testNickNameUpdate");
        pfUserAdminDO.setLastLoginIp("192.168.0.1");
        pfUserAdminDO.setLastLoginTime(new Date());
        boolean r=pfUserAdminDAO.update(pfUserAdminDO);
        Assert.assertTrue(r);

    }
    @Test
    public void testSelectById(){
        long id=1;
        PfUserAdminDO concurPlanDO=(PfUserAdminDO)pfUserAdminDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }

    @Test
    public void testDeleteById(){

    }

    @Test
    public void testQueryByLoginName(){
        PfUserAdminQueryDO queryDO = new PfUserAdminQueryDO();
        queryDO.setLoginName("test");
        //queryDO.setAppId("testappid");

        List<UserAppOpenIdDO> list= pfUserAdminDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }





}
