package com.line.bqxd.platform.controller.v2.wx;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by danny on 10/22/16.
 */
public class WXTemplate {
    private String template_id;
    private String touser;
    private String url;
    private String topcolor;
    private Map<String,TemplateData> data;

    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTopcolor() {
        return topcolor;
    }
    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }
    public Map<String,TemplateData> getData() {
        return data;
    }
    public void setData(Map<String,TemplateData> data) {
        this.data = data;
    }


    public WXTemplate(String template_id)
    {this.template_id = template_id;}


    public void fillData(String userId)
    {
        WXTemplate t = this;
        t.setUrl("");
        t.setTouser(userId);
        t.setTopcolor("#000000");
        t.setTemplate_id(this.getTemplate_id());
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData first = new TemplateData();
        first.setColor("#000000");
        first.setValue("***标题***");
        m.put("first", first);
        TemplateData name = new TemplateData();
        name.setColor("#000000");
        name.setValue("***名称***");
        m.put("name", name);
        TemplateData remark = new TemplateData();
        remark.setColor("blue");
        remark.setValue("***备注说明***");
        m.put("Remark", remark);
        t.setData(m);
/*
        JSONObject.fromObject(t).toString();
        */
    }
}

