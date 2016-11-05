package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.PfCorpDO;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.client.dataobject.query.PfCorpQueryDO;
import com.line.bqxd.platform.client.dataobject.query.PfWeixinAuthQueryDO;
import com.line.bqxd.platform.dao.PfCorpDAO;
import com.line.bqxd.platform.dao.PfWeixinAuthDAO;
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
public class PfWeixinAuthDAOTest {

    @Resource  //自动注入,默认按名称
    private PfWeixinAuthDAO<PfWeixinAuthDO> pfWeixinAuthDAO;

    @Test
    public void testInsert() {
        PfWeixinAuthDO pfWeixinAuthDO = new PfWeixinAuthDO();
        pfWeixinAuthDO.setNickName("testNickName");
        pfWeixinAuthDO.setCorpId(123);
        pfWeixinAuthDO.setAlias("testAlias");
        pfWeixinAuthDO.setAuthorizerAppid("testAuthorizerAppid");
        pfWeixinAuthDO.setAuthorizerAccessToken("testAuthorizerAccessToken");
        pfWeixinAuthDO.setAuthorizerRefreshToken("testAuthorizerRefreshToken");
        pfWeixinAuthDO.setExpiresIn(7200);
        pfWeixinAuthDO.setBusinessInfo("testBusinessInfo");
        pfWeixinAuthDO.setFuncInfo("testFuncInfo");
        pfWeixinAuthDO.setHeadImg("http://headImg");
        pfWeixinAuthDO.setQrcodeUrl("testQrcodeUrl");
        pfWeixinAuthDO.setServiceTypeInfo(1);
        pfWeixinAuthDO.setUserName("testUserName");
        pfWeixinAuthDO.setVerifyTypeInfo(1);
        long r = pfWeixinAuthDAO.insert(pfWeixinAuthDO);
        Assert.assertTrue(r > 0);
    }

    @Test
    public void testUpdate() {

        PfWeixinAuthDO pfWeixinAuthDO = new PfWeixinAuthDO();
        pfWeixinAuthDO.setId(2);
        pfWeixinAuthDO.setNickName("testNickNameUpdate");
        //pfWeixinAuthDO.setCorpId(12345);
        pfWeixinAuthDO.setAlias("testAliasUpdate");
        pfWeixinAuthDO.setAuthorizerAppid("testUpdateAuthorizerAppid");
        pfWeixinAuthDO.setAuthorizerAccessToken("testUpdateAuthorizerAccessToken");
        pfWeixinAuthDO.setAuthorizerRefreshToken("testUpdateAuthorizerRefreshToken");
        pfWeixinAuthDO.setExpiresIn(7300);
        pfWeixinAuthDO.setBusinessInfo("testBusinessInfoUpdate");
        pfWeixinAuthDO.setFuncInfo("testFuncInfoUpdate");
        pfWeixinAuthDO.setHeadImg("http://headImgUpdate");
        pfWeixinAuthDO.setQrcodeUrl("testQrcodeUrlUpdate");
        pfWeixinAuthDO.setServiceTypeInfo(2);
        pfWeixinAuthDO.setUserName("testUserNameUpdate");
        pfWeixinAuthDO.setVerifyTypeInfo(3);
        boolean r = pfWeixinAuthDAO.update(pfWeixinAuthDO);
        Assert.assertTrue(r);

    }

    @Test
    public void testUpdateByAppId() {


        PfWeixinAuthDO pfWeixinAuthDO = new PfWeixinAuthDO();
        pfWeixinAuthDO.setNickName("testNickNameUpdate2");
        //pfWeixinAuthDO.setCorpId(12345);
        pfWeixinAuthDO.setAlias("testAliasUpdate2");
        pfWeixinAuthDO.setAuthorizerAppid("testAppIdUpdate");
        pfWeixinAuthDO.setBusinessInfo("testBusinessInfoUpdate2");
        pfWeixinAuthDO.setFuncInfo("testFuncInfoUpdate2");
        pfWeixinAuthDO.setHeadImg("http://headImgUpdate2");
        pfWeixinAuthDO.setQrcodeUrl("testQrcodeUrlUpdate2");
        pfWeixinAuthDO.setServiceTypeInfo(3);
        pfWeixinAuthDO.setUserName("testUserNameUpdate2");
        pfWeixinAuthDO.setVerifyTypeInfo(4);
        boolean r = pfWeixinAuthDAO.updateByAppId(pfWeixinAuthDO);
        Assert.assertTrue(r);

    }

    @Test
    public void testSelectById() {
        long id = 1;
        PfWeixinAuthDO concurPlanDO = (PfWeixinAuthDO) pfWeixinAuthDAO.selectById(id);
        Assert.assertTrue(concurPlanDO != null);
        Assert.assertTrue((concurPlanDO.getId() > 0));
    }

    @Test
    public void testDeleteById() {

    }

    @Test
    public void testQueryByLoginName() {
        PfWeixinAuthQueryDO queryDO = new PfWeixinAuthQueryDO();
        queryDO.setAuthorizerAppid("testAppIdUpdate");
        //queryDO.setAppId("testappid");

        List<PfWeixinAuthDO> list = pfWeixinAuthDAO.selectByQuery(queryDO);

        Assert.assertTrue(list != null);

        Assert.assertTrue(list.size() > 0);
    }

}
