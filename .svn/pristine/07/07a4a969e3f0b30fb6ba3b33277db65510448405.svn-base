package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.dao.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangjianfei on 16/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserTest {
    @Resource  //自动注入,默认按名称
    private UserDAO userDAO;

    @Test
    public void testGetUserByOpenId(){
        UserDO userDO=userDAO.selectByOpenId("ussdfsdfs");

        Assert.assertNotNull(userDO);
    }
    @Test
    public void testInsertUser(){
        UserDO userDO = new UserDO();
        long now = System.currentTimeMillis();

        userDO.setCity("杭州");
        userDO.setCountry("cn");
        userDO.setHeadImgurl(now+"headimgurl");
        userDO.setMobile("13777495730");
        userDO.setNickName("luohai");
        //userDO.setOpenid(now+"openid");
        userDO.setProvince("浙江");
        userDO.setUserName("黄建飞");
        userDO.setSex(1);
        userDO.setUnionid(now+"unionid");
        userDO.setCard("330182198101010510");
        userDO.setStatus(1);


        userDO.setInviteUserId(23);

        userDO.setLevel(2);

        long id=userDAO.insert(userDO);
        Assert.assertFalse(id<=0);

    }

    @Test
    public void testUpdate(){
        long id=17;
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setUserId(38);
        userDO.setUserName("罗海");
        /*
        long now = System.currentTimeMillis();

        userDO.setCard(now+"now");
        userDO.setMobile("13766348765");
        userDO.setLevel(7);
        userDO.setIsWxattention(4);
        userDO.setInviteUserId(456);
        userDO.setExsitSubUser(5);
        userDO.setCard("330182198201010529");
        userDO.setStatus(9);
        userDO.setUserType(6);
        */
        boolean result=userDAO.update(userDO);
        Assert.assertFalse(!result);
    }

    @Test
    public void testUpdateByOpenid(){
        String openId="ot3tXs0AZNShis5r4mG6muF-5hEc";
        UserDO userDO = new UserDO();
        userDO.setOpenid(openId);
        userDO.setUserName("罗海");
        /*
        long now = System.currentTimeMillis();

        userDO.setCard(now+"now");
        userDO.setMobile("13766348765");
        */
        boolean result=userDAO.updateByOpenid(userDO);
        Assert.assertFalse(!result);
    }

    @Test
    public void testSelectByQuery(){
        UserQueryDO userQueryDO= new UserQueryDO();
        userQueryDO.setCard("330182198101010519");
        List<UserDO> list=userDAO.selectByQuery(userQueryDO);
        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);
    }
}
