package com.line.bqxd.platform.dao.test;

import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.WXAttentionDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.client.dataobject.query.WXAttentionQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.dao.WXAttentionDAO;
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
public class WXAttentionDAOtest {
    @Resource  //自动注入,默认按名称
    private WXAttentionDAO wxAttentionDAO;

    @Test
    public void testInsert(){
        WXAttentionDO claimDO = new WXAttentionDO();
        claimDO.setOpenId(String.valueOf(System.currentTimeMillis()));
        claimDO.setTag("serve");
        long r=wxAttentionDAO.insert(claimDO);
        Assert.assertTrue(r>0);
    }



    @Test
    public void testSelectByQuery(){
        WXAttentionQueryDO wxAttentionQueryDO = new WXAttentionQueryDO();

        wxAttentionQueryDO.setOpenId("1463450208518");
        wxAttentionQueryDO.setTag("serve");
        List<CommentsDO> list=wxAttentionDAO.selectByQuery(wxAttentionQueryDO);

        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()>0);

    }



    @Test
    public void testDeleteByOpenId(){
        boolean rt=wxAttentionDAO.deleteByOpenId("1463450208518");
        Assert.assertTrue(rt);
    }
}
