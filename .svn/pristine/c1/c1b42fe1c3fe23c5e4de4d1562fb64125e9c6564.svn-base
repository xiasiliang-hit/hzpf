package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.dao.ConcurPlanDAO;
import com.line.bqxd.platform.dao.UserDAO;
import org.apache.commons.lang.StringUtils;
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
public class ConcurPlanDAOTest {

    @Resource  //自动注入,默认按名称
    private ConcurPlanDAO<ConcurPlanDO> concurPlanDAO;

    @Test
    public void testInsert(){

        ConcurPlanDO concurPlanDO = new ConcurPlanDO();
        concurPlanDO.setPfAppId(1);
        concurPlanDO.setEnsureName("视网膜病变");
        concurPlanDO.setBannerUrl("http://res.xiongdihuzhu.com/res/xdpf/images/Z-banner1.png");
        concurPlanDO.setConcurDescUrl("http://res.xiongdihuzhu.com/res/xdpf/images/donate@2x.png");
        concurPlanDO.setTenetUrl("http://res.xiongdihuzhu.com/res/xdpf/images/zongzhi@2x.png");
        concurPlanDO.setServiceUrl("http://res.xiongdihuzhu.com/res/xdpf/images/serve@2x.png");
        concurPlanDO.setCostMoney(500);
        concurPlanDO.setCostMoneyDesc("单次不超过50元");
        concurPlanDO.setEnsureContinue(1);
        concurPlanDO.setEnsureContinueDesc("账户余额不少于1元");
        concurPlanDO.setEnsureMoney(10000);
        concurPlanDO.setEnsureRequire("出生0-7天的早产儿（无等待期），7天-30天新生儿（30天等待期）");
        concurPlanDO.setEnsureTime("出生7天-365天");
        concurPlanDO.setFirstStoreMoney(600);
        concurPlanDO.setFirstStoreMoneyDesc("600元");
        concurPlanDO.setName("早安宝贝早产儿");
        long r=concurPlanDAO.insert(concurPlanDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        ConcurPlanDO concurPlanDO= new ConcurPlanDO();
        concurPlanDO.setId(3);
        concurPlanDO.setName("testNameUpdate");


        boolean r=concurPlanDAO.update(concurPlanDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectById(){
        long id=2;
        ConcurPlanDO concurPlanDO=(ConcurPlanDO)concurPlanDAO.selectById(id);
        Assert.assertTrue(concurPlanDO!=null);
        Assert.assertTrue(StringUtils.isNotBlank(concurPlanDO.getName()));
        //List<ConcurPlanDO> list=concurPlanDAO.selectAll();
    }

    @Test
    public void testDeleteById(){
        long id=2;
        boolean r=concurPlanDAO.delete(id);
        Assert.assertTrue(r);
    }
}
