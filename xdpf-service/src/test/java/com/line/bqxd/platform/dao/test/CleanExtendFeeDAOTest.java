package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.CleanExtendFeeDO;
import com.line.bqxd.platform.client.dataobject.ExtendDO;
import com.line.bqxd.platform.client.dataobject.UserActivityDO;
import com.line.bqxd.platform.client.dataobject.query.CleanExtendFeeQueryDO;
import com.line.bqxd.platform.client.dataobject.query.PfCorpQueryDO;
import com.line.bqxd.platform.dao.CleanExtendFeeDAO;
import com.line.bqxd.platform.dao.ExtendDAO;
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
public class CleanExtendFeeDAOTest {

    @Resource  //自动注入,默认按名称
    private CleanExtendFeeDAO<CleanExtendFeeDO> cleanExtendFeeDAO;

    @Test
    public void testInsert(){
        CleanExtendFeeDO cleanExtendFeeDO = new CleanExtendFeeDO();

        cleanExtendFeeDO.setFee(100000);
        cleanExtendFeeDO.setClearDate("20160729");
        cleanExtendFeeDO.setDescr("errDescr");
        cleanExtendFeeDO.setExplains("testExplains");
        cleanExtendFeeDO.setExtendId(1);
        cleanExtendFeeDO.setStatus(1);
        long r=cleanExtendFeeDAO.insert(cleanExtendFeeDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
       // PfUserAdminDO pfUserAdminDO = new PfUserAdminDO();
        CleanExtendFeeDO cleanExtendFeeDO = new CleanExtendFeeDO();

        cleanExtendFeeDO.setId(1);
        cleanExtendFeeDO.setFee(110000);
        cleanExtendFeeDO.setClearDate("20160720");
        cleanExtendFeeDO.setDescr("errDescrUpdate");
        cleanExtendFeeDO.setExplains("testExplainsUpdate");
        cleanExtendFeeDO.setExtendId(3);
        cleanExtendFeeDO.setStatus(2);
        boolean r=cleanExtendFeeDAO.update(cleanExtendFeeDO);
        Assert.assertTrue(r);

    }

    @Test
    public void testSelectById(){
        long id=1;
        CleanExtendFeeDO concurPlanDO=(CleanExtendFeeDO)cleanExtendFeeDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }

    @Test
    public void testselectAll(){
        List<CleanExtendFeeDO> list=cleanExtendFeeDAO.selectAll();

        Assert.assertTrue(list!=null);
        Assert.assertTrue((list.size()>0));
    }

    @Test
    public void testSelectByQuery(){
        CleanExtendFeeQueryDO queryDO = new CleanExtendFeeQueryDO();
        queryDO.setExtendId(1);
        queryDO.setStatus(2);
        queryDO.setClearDate("20160721");
        List<CleanExtendFeeDO> list= cleanExtendFeeDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }





}
