package com.line.bqxd.platform.manager.wxpay;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.manager.wxmenu.WXMenuDO;
import com.line.bqxd.platform.manager.wxmenu.WXMenuManager;
import com.line.bqxd.platform.manager.wxmenu.impl.WXMenuManagerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class MenuManagerTest {

    @Resource  //自动注入,默认按名称
    private WXMenuManager wxmenuManager;

    @Test
    public void testCreateMenu(){
        WXMenuDO wxMenuDO = new WXMenuDO();
        wxMenuDO.setName("授权菜单");
        wxMenuDO.setUrl("http://www.xiongdihuzhu.com/safety/test.htm");
        wxMenuDO.setType("view");
        WXMenuDO wxMenuDO2 = new WXMenuDO();
        wxMenuDO2.setName("支付菜单");
        wxMenuDO2.setType("click");

        WXMenuDO wxMenuDO3 = new WXMenuDO();
        wxMenuDO3.setName("支付菜单");
        wxMenuDO3.setUrl("http://www.xiongdihuzhu.com/safety/pay/test2.htm");
        wxMenuDO3.setType("view");
        wxMenuDO2.addSubMenu(wxMenuDO3);
        List<WXMenuDO> wxMenuDOList= new ArrayList<WXMenuDO>();
        wxMenuDOList.add(wxMenuDO);
        wxMenuDOList.add(wxMenuDO2);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("button",wxMenuDOList);
        //map.put("name","菜单测试");

        /*
        WXMenuDO wxMenuDOAll = new WXMenuDO();
        wxMenuDOAll.setName("菜单测试");
        wxMenuDOAll.setButton("button");
        wxMenuDOAll.setSub_button(wxMenuDOList);
        */
        wxmenuManager.createMenu(map);
        System.out.println(JSON.toJSONString(map));
    }
    //生产环境菜单,请慎用,必须对照下 biz-manager.xml配置是否是线上配置
    @Test
    public void testCreateBqxdMenu()throws Exception{
        WXMenuDO wxMenuDO = new WXMenuDO();
        wxMenuDO.setName("互助计划");
        wxMenuDO.setUrl("http://www.xiongdihuzhu.com/concur/detail.htm?channelId=12");
        wxMenuDO.setType("view");
        WXMenuDO wxMenuDO2 = new WXMenuDO();
        wxMenuDO2.setName("我的互助");
        wxMenuDO2.setType("view");
        wxMenuDO2.setUrl("http://www.xiongdihuzhu.com/myconcur/index.htm?channelId=12");
        WXMenuDO wxMenuDO3 = new WXMenuDO();
        wxMenuDO3.setName("分享");
        wxMenuDO3.setType("view");
        wxMenuDO3.setUrl("http://www.xiongdihuzhu.com/activity/ali.htm?channelId=12");

        List<WXMenuDO> wxMenuDOList= new ArrayList<WXMenuDO>();
        wxMenuDOList.add(wxMenuDO);
        wxMenuDOList.add(wxMenuDO2);
        //wxMenuDOList.add(wxMenuDO3);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("button",wxMenuDOList);
        //map.put("name","菜单测试");

        /*
        WXMenuDO wxMenuDOAll = new WXMenuDO();
        wxMenuDOAll.setName("菜单测试");
        wxMenuDOAll.setButton("button");
        wxMenuDOAll.setSub_button(wxMenuDOList);
        */
        Thread.sleep(5000);
        wxmenuManager.createMenu(map);
        System.out.println(JSON.toJSONString(map));
    }


    @Test
    public void testCreateBqxdMenuTestEnv()throws Exception{
        WXMenuDO wxMenuDO = new WXMenuDO();
        wxMenuDO.setName("互助计划");
        wxMenuDO.setUrl("http://www.xiantiaokeji.com/concur/detail.htm?channelId=12");
        wxMenuDO.setType("view");
        WXMenuDO wxMenuDO2 = new WXMenuDO();
        wxMenuDO2.setName("我的互助");
        wxMenuDO2.setType("view");
        wxMenuDO2.setUrl("http://www.xiantiaokeji.com/myconcur/index.htm?channelId=12");
        WXMenuDO wxMenuDO3 = new WXMenuDO();
        wxMenuDO3.setName("分享");
        wxMenuDO3.setType("view");
        wxMenuDO3.setUrl("http://www.xiantiaokeji.com/activity/ali.htm?channelId=12");

        List<WXMenuDO> wxMenuDOList= new ArrayList<WXMenuDO>();
        wxMenuDOList.add(wxMenuDO);
        wxMenuDOList.add(wxMenuDO2);
        wxMenuDOList.add(wxMenuDO3);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("button",wxMenuDOList);
        //map.put("name","菜单测试");

        /*
        WXMenuDO wxMenuDOAll = new WXMenuDO();
        wxMenuDOAll.setName("菜单测试");
        wxMenuDOAll.setButton("button");
        wxMenuDOAll.setSub_button(wxMenuDOList);
        */
        Thread.sleep(5000);
        BizResult<Boolean> result= wxmenuManager.createMenu(map);
        System.out.println(result);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testGetMenu()throws Exception{
        Thread.sleep(5000);
        String str=wxmenuManager.getMenu();
        System.out.println(str);
    }
}
