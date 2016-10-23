package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.CleanExtendFeeDO;
import com.line.bqxd.platform.client.dataobject.DunkBillDO;
import com.line.bqxd.platform.client.dataobject.query.CleanExtendFeeQueryDO;
import com.line.bqxd.platform.client.dataobject.query.DunkBillQueryDO;
import com.line.bqxd.platform.dao.CleanExtendFeeDAO;
import com.line.bqxd.platform.dao.DunkBillDAO;
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
public class DunkBillDAOTest {

    @Resource  //自动注入,默认按名称
    private DunkBillDAO<DunkBillDO> dunkBillDAO;

    @Test
    public void testInsert(){
        DunkBillDO dunkBillDO = new DunkBillDO();
        dunkBillDO.setBalance(100);
        dunkBillDO.setUserId(12);
        dunkBillDO.setConcurPlanId(23);
        dunkBillDO.setUserConcurRelationId(45);
        dunkBillDO.setUserClaimId(34);

        long r=dunkBillDAO.insert(dunkBillDO);
        Assert.assertTrue(r>0);
    }



    @Test
    public void testSelectById(){
        long id=1;
        DunkBillDO concurPlanDO=(DunkBillDO)dunkBillDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue((concurPlanDO.getId()>0));
    }




    @Test
    public void testSelectByQuery(){
        DunkBillQueryDO queryDO =new DunkBillQueryDO();
        queryDO.setUserId(12);
        List<CleanExtendFeeDO> list= dunkBillDAO.selectByQuery(queryDO);

        Assert.assertTrue(list!=null);

        Assert.assertTrue(list.size()>0);
    }


    @Test
    public void testSelectStatistics(){
        DunkBillQueryDO queryDO =new DunkBillQueryDO();
        queryDO.setUserId(123);
        long rt= dunkBillDAO.selectStatistics(queryDO);


        Assert.assertTrue(rt>0);
    }


}
