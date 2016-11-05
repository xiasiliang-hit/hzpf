package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.ConcurRemindDO;
import com.line.bqxd.platform.client.dataobject.UserTradePayDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.client.dataobject.query.ConcurRemindQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.dao.ConcurRemindDAO;
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
public class CommentsDAOtest {
    @Resource  //自动注入,默认按名称
    private CommentsDAO commentsDAO;

    @Test
    public void testInsert(){
        CommentsDO claimDO = new CommentsDO();
        claimDO.setStatus(2);
        claimDO.setBizType("biztype");
        claimDO.setContent("content");
        claimDO.setFavourCount(12);
        claimDO.setOutBizId("outbizid");
        claimDO.setUserId(123);
        claimDO.setNickName("userName");
        claimDO.setHeadImgUrl("ddddd");
        long r=commentsDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }

    @Test
    public void testUpdate(){
        CommentsDO claimDO = new CommentsDO();
        claimDO.setId(1);
        claimDO.setStatus(3);

        claimDO.setContent("contentupdate");

        boolean r=commentsDAO.update(claimDO);
        Assert.assertTrue(r);
    }
    @Test
    public void testSelectByQuery(){
        CommentsQueryDO userClaimApplyQueryDO = new CommentsQueryDO();

        userClaimApplyQueryDO.setStatus(2);
        userClaimApplyQueryDO.setNickName("userName");
        userClaimApplyQueryDO.setUserId(123);
        userClaimApplyQueryDO.setBizType("biztype");
        userClaimApplyQueryDO.setOutBizId("outbizid");
        List<CommentsDO> list=commentsDAO.selectByQuery(userClaimApplyQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }

    @Test
    public void testCountByQuery(){
        CommentsQueryDO userClaimApplyQueryDO = new CommentsQueryDO();

        userClaimApplyQueryDO.setStatus(2);
        userClaimApplyQueryDO.setNickName("userName");
        userClaimApplyQueryDO.setBizType("biztype");
        userClaimApplyQueryDO.setUserId(123);
        userClaimApplyQueryDO.setOutBizId("outbizid");

        int r=commentsDAO.countByQuery(userClaimApplyQueryDO);


        Assert.assertTrue(r>0);

    }

    @Test
    public void testSelectById(){
        CommentsDO claimApplyDO=(CommentsDO) commentsDAO.selectById(1);
        Assert.assertTrue(claimApplyDO!=null);
        Assert.assertTrue(claimApplyDO.getId()==1);
    }
}
