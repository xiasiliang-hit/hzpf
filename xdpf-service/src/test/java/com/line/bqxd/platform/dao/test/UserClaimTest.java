package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.ClaimDO;
import com.line.bqxd.platform.dao.UserClaimDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huangjianfei on 16/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class UserClaimTest {
    @Resource  //自动注入,默认按名称
    private UserClaimDAO<ClaimDO> userClaimDAO;

    @Test
    public void testInsert(){
        ClaimDO claimDO = new ClaimDO();
        claimDO.setAccount("123213");
        claimDO.setClaimApplyId(3);
        claimDO.setClaimCompany("testcompany");
        claimDO.setClaimCompanyFee(44);
        claimDO.setClaimCompanyTel("13777495730");
        claimDO.setClaimCompanyUser("testUser");
        claimDO.setData("sdfdsfdsf");
        claimDO.setFeeToType(2);
        claimDO.setOtherFee(45);
        claimDO.setTotalFee(56);
        claimDO.setUserFee(34);
        claimDO.setPublishUrl("sdfsdfsdf");
        long r=userClaimDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        ClaimDO claimDO = new ClaimDO();
        claimDO.setId(1);
        claimDO.setAccount("1232131234update");
        claimDO.setClaimApplyId(3);
        claimDO.setClaimCompany("testcompanyupdate");
        claimDO.setClaimCompanyFee(4400);
        claimDO.setClaimCompanyTel("13777495730update");
        claimDO.setClaimCompanyUser("testUserupdate");
        claimDO.setData("sdfdsfdsfupdate");
        claimDO.setFeeToType(200);
        claimDO.setOtherFee(4500);
        claimDO.setTotalFee(5600);
        claimDO.setUserFee(3400);
        boolean r=userClaimDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectById(){
        long id=1;
        ClaimDO claimDO=(ClaimDO)userClaimDAO.selectById(id);
        Assert.assertTrue(claimDO!=null);
        Assert.assertTrue(claimDO.getId()==id);
    }


}
