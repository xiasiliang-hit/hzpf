package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.UserActivityDO;
import com.line.bqxd.platform.client.dataobject.query.PfCorpQueryDO;
import com.line.bqxd.platform.dao.ExtendDAO;
import com.line.bqxd.platform.dao.UserActivityDAO;
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
public class ExtendDAOTest {

    @Resource  //自动注入,默认按名称
    private ExtendDAO<ExtendDO> extendDAO;

    @Test
    public void testInsert(){
        ExtendDO extendDO = new ExtendDO();
        extendDO.setFee(100);
        extendDO.setDescr("descr");
        extendDO.setCorpName("线条科技");
        extendDO.setContactName("罗海");
        extendDO.setMobile("13777495730");
        extendDO.setType(1);
        long r=extendDAO.insert(extendDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
       // PfUserAdminDO pfUserAdminDO = new PfUserAdminDO();
        ExtendDO extendDO = new ExtendDO();
        extendDO.setId(1);
        extendDO.setFee(1000);
        extendDO.setDescr("descrUpdate");
        extendDO.setCorpName("线条科技Update");
        extendDO.setContactName("罗海Update");
        extendDO.setMobile("13777495731");
        extendDO.setType(2);
        boolean r=extendDAO.update(extendDO);
        Assert.assertTrue(r);

    }

    @Test
    public void testSelectById(){
        long id=1;
        ExtendDO concurPlanDO=(ExtendDO)extendDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }

    @Test
    public void testselectAll(){
        List<ExtendDO> list=extendDAO.selectAll();

        Assert.assertTrue(list!=null);
        Assert.assertTrue((list.size()>0));
    }

    @Test
    public void testQueryByLoginName(){
        PfCorpQueryDO queryDO = new PfCorpQueryDO();
        queryDO.setCorpName("线条科技");
        //queryDO.setAppId("testappid");

        List<UserActivityDO> list= extendDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }





}
