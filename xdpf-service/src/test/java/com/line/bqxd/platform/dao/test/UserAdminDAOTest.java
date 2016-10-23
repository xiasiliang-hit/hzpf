package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserAdminDO;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.dao.UserAdminDAO;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by huangjianfei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserAdminDAOTest {

    @Resource  //自动注入,默认按名称
    private UserAdminDAO<UserAdminDO> userAdminDAO;

    @Test
    public void testInsert() throws Exception{

        UserAdminDO concurPlanDO = new UserAdminDO();
        concurPlanDO.setLoginName("admin");
        concurPlanDO.setShowName("admin");
        concurPlanDO.setPassword("admin123");
        concurPlanDO.setLastLoginTime(DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT1,new Date()));
        InetAddress ia=InetAddress.getLocalHost();

        concurPlanDO.setLastLoginIp(ia.getHostAddress());
        long r = userAdminDAO.insert(concurPlanDO);
        Assert.assertTrue(r > 0);
    }

    @Test
    public void testUpdate() throws Exception{
        UserAdminDO concurPlanDO = new UserAdminDO();
        concurPlanDO.setLoginName("admin");
        concurPlanDO.setShowName("admin4");
        concurPlanDO.setPassword("admin1234");
        concurPlanDO.setLastLoginTime(DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT1,new Date()));
        InetAddress ia=InetAddress.getLocalHost();

        concurPlanDO.setLastLoginIp(ia.getHostAddress());

        boolean r = userAdminDAO.update(concurPlanDO);
        Assert.assertTrue(r);
    }

    @Test
    public void testSelectById() {
        long id = 1;
        UserAdminDO concurPlanDO = (UserAdminDO) userAdminDAO.selectById(id);
        Assert.assertTrue(concurPlanDO != null);
        Assert.assertTrue(StringUtils.isNotBlank(concurPlanDO.getLoginName()));
    }



    @Test
    public void testgetUserAdminByLoginName() {
        UserAdminDO userAdminDO = userAdminDAO.getUserAdminByLoginName("admin");

        Assert.assertTrue(userAdminDO != null);

        Assert.assertTrue(StringUtils.isNotBlank(userAdminDO.getLoginName()));
    }


}
