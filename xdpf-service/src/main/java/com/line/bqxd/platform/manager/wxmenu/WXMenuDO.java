package com.line.bqxd.platform.manager.wxmenu;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Created by huangjianfei on 16/5/4.
 */
public class WXMenuDO {
    private String button;
    private String  name;

    private String type;
    private String  key;

    private String url;

    private List<WXMenuDO> sub_button;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WXMenuDO> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WXMenuDO> sub_button) {
        this.sub_button = sub_button;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addSubMenu(WXMenuDO wxMenuDO) {
        if (sub_button == null) {
            sub_button = new ArrayList<WXMenuDO>();
        }
        sub_button.add(wxMenuDO);
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public static void main(String[] args){
        WXMenuDO wxMenuDO = new WXMenuDO();
        wxMenuDO.setName("test1");
        wxMenuDO.setUrl("www.taobao.com");

        WXMenuDO wxMenuDO2 = new WXMenuDO();
        wxMenuDO2.setName("test1");
        wxMenuDO2.setUrl("www.taobao.com");

        WXMenuDO wxMenuDO3 = new WXMenuDO();
        wxMenuDO3.setName("test1");
        wxMenuDO3.setUrl("www.taobao.com");

        wxMenuDO2.addSubMenu(wxMenuDO3);
        List<WXMenuDO> wxMenuDOList= new ArrayList<WXMenuDO>();
        wxMenuDOList.add(wxMenuDO);
        wxMenuDOList.add(wxMenuDO2);
        Map<String,List<WXMenuDO>> map = new HashMap<String,List<WXMenuDO>>();
        map.put("button",wxMenuDOList);
        System.out.println(JSON.toJSONString(map));
    }
}
