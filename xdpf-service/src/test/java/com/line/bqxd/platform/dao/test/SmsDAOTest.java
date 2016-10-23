package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.SmsDO;
import com.line.bqxd.platform.dao.SmsDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huangjianfei on 16/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class SmsDAOTest {

    @Resource
    private SmsDAO smsDAO;

    @Test
    public void testInsert(){
        SmsDO smsDO = new SmsDO();
        smsDO.setBizType("reg");
        smsDO.setStatus(1);
        smsDO.setRecNum("13777495730");
        smsDO.setChannel("ali");
        smsDO.setCode("1234");
        smsDO.setData("test");
        smsDO.setSignName("testSign");
        smsDO.setTemplateId("testTemplateId");
        long id=smsDAO.insert(smsDO);
        Assert.assertTrue(id>0);
    }

    @Test
    public void testUpdate(){
        SmsDO smsDO = new SmsDO();
        smsDO.setId(1);
        smsDO.setStatus(2);
        smsDO.setErrCode("100");
        smsDO.setMsg("testMsg");
        boolean rt=smsDAO.update(smsDO);
        Assert.assertTrue(rt);
    }

    @Test
    public void testgetSmsByRecNumAndStatus(){
        SmsDO smsDO=smsDAO.getSmsByRecNumAndStatus("13777495730",2);
        Assert.assertTrue(smsDO!=null);
    }
}
