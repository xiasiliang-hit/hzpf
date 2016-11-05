package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ConcurRemindDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.ConcurRemindQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradePayQueryDO;
import com.line.bqxd.platform.dao.ConcurRemindDAO;
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
public class ConcurRemindDAOtest {
    @Resource  //自动注入,默认按名称
    private ConcurRemindDAO concurRemindDAO;

    @Test
    public void testInsert(){
        ConcurRemindDO claimDO = new ConcurRemindDO();
        claimDO.setStatus(2);
        claimDO.setOutBizId("123");
        claimDO.setBizType("biztype");
        claimDO.setData("testdata");
        claimDO.setDynamic("yes");
        claimDO.setMsgType(1);
        claimDO.setTemplateId("templateid");
        claimDO.setTemplateType("wx");
        claimDO.setTitle("title");
        claimDO.setUrl("http");
        long r=concurRemindDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        ConcurRemindDO claimDO = new ConcurRemindDO();
        claimDO.setId(1);
        claimDO.setStatus(3);
        claimDO.setOutBizId("123update");
        claimDO.setBizType("biztypeupdate");
        claimDO.setData("testdataupdate");
        claimDO.setDynamic("yesupdate");
        claimDO.setMsgType(2);
        claimDO.setTemplateId("templateidupdate");
        claimDO.setTemplateType("wxupdate");
        claimDO.setTitle("titleupdate");
        claimDO.setUrl("httpupdate");
        boolean r=concurRemindDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        ConcurRemindQueryDO userClaimApplyQueryDO = new ConcurRemindQueryDO();
        userClaimApplyQueryDO.setTitle("titleupdate");
        userClaimApplyQueryDO.setStatus(3);
        userClaimApplyQueryDO.setMsgType(2);
        List<UserTradePayDO> list=concurRemindDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        ConcurRemindQueryDO userClaimApplyQueryDO = new ConcurRemindQueryDO();
        userClaimApplyQueryDO.setTitle("titleupdate");
        userClaimApplyQueryDO.setStatus(3);
        userClaimApplyQueryDO.setMsgType(2);
        int r=concurRemindDAO.countByQuery(userClaimApplyQueryDO);


        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        ConcurRemindDO claimApplyDO=(ConcurRemindDO) concurRemindDAO.selectById(1);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==1);
    }
}
