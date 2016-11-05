package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.PfCorpDO;
import com.line.bqxd.platform.client.dataobject.PfUserAdminDO;
import com.line.bqxd.platform.client.dataobject.UserAppOpenIdDO;
import com.line.bqxd.platform.client.dataobject.query.PfCorpQueryDO;
import com.line.bqxd.platform.client.dataobject.query.PfUserAdminQueryDO;
import com.line.bqxd.platform.dao.PfCorpDAO;
import com.line.bqxd.platform.dao.PfUserAdminDAO;
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
public class PfCorpDAOTest {

    @Resource  //自动注入,默认按名称
    private PfCorpDAO<PfCorpDO> pfCorpDAO;

    @Test
    public void testInsert(){
        PfCorpDO pfCorpDO = new PfCorpDO();
        pfCorpDO.setContactName("testContactName");
        pfCorpDO.setCorpLogo("http://logo");
        pfCorpDO.setCorpName("线条科技");
        pfCorpDO.setMobile("13777495730");
        pfCorpDO.setTel("12345678");
        pfCorpDO.setStaffSum(2000);
        pfCorpDO.setPayType(1);
        pfCorpDO.setPayMchId("testMchId");
        pfCorpDO.setPayMchSecret("testMchSecret");
        long r=pfCorpDAO.insert(pfCorpDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
       // PfUserAdminDO pfUserAdminDO = new PfUserAdminDO();
        PfCorpDO pfCorpDO = new PfCorpDO();
        pfCorpDO.setId(1);
        pfCorpDO.setContactName("testContactNameUpdate");
        pfCorpDO.setCorpLogo("http://logoUpdate");
        pfCorpDO.setCorpName("线条科技Update");
        pfCorpDO.setMobile("13777495731");
        pfCorpDO.setTel("12345679");
        pfCorpDO.setStaffSum(20001);
        pfCorpDO.setPayType(2);
        pfCorpDO.setPayMchId("testMchIdUpdate");
        pfCorpDO.setPayMchSecret("testMchSecretUpdate");
        boolean r=pfCorpDAO.update(pfCorpDO);
        Assert.assertTrue(r);

    }

    @Test
    public void testSelectById(){
        long id=1;
        PfCorpDO concurPlanDO=(PfCorpDO)pfCorpDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }

    @Test
    public void testDeleteById(){

    }

    @Test
    public void testQueryByLoginName(){
        PfCorpQueryDO queryDO = new PfCorpQueryDO();
        queryDO.setCorpName("线条科技");
        //queryDO.setAppId("testappid");

        List<PfCorpDO> list= pfCorpDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }





}
