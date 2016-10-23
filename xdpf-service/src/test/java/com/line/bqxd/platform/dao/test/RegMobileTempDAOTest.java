package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.RegMobileTempDO;
import com.line.bqxd.platform.dao.RegMobileTempDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huangjianfei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class RegMobileTempDAOTest {

    @Resource  //自动注入,默认按名称
    private RegMobileTempDAO<RegMobileTempDO> regMobileTempDAO;

    @Test
    public void testInsert() {
        RegMobileTempDO concurPlanDO = new RegMobileTempDO();
        concurPlanDO.setMobile("13777495730");
        long r = regMobileTempDAO.insert(concurPlanDO);
        Assert.assertTrue(r > 0);
    }


    @Test
    public void testSelectByMobile() {
        RegMobileTempDO regMobileTempDO = regMobileTempDAO.selectByMobile("13777495730");
        Assert.assertTrue(regMobileTempDO !=null);
    }


    @Test
    public void testDeleteByMobile() {
        boolean rt = regMobileTempDAO.deleteByMobile("13777495730");
        Assert.assertTrue(rt);
    }
}
