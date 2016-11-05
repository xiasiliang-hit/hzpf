package com.line.bqxd.platform.manager.wx.dataobject;

import com.line.bqxd.platform.client.common.Base;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjianfei on 16/7/12.
 */
public class TemplateMsgDO extends Base implements Serializable {

    private static final long serialVersionUID = -7755889617118924643L;

    private static final String FIRST_KEY = "first";

    private static final String REMARK_KEY = "remark";

    private String touser;

    private String template_id;

    private String url;

    private Map<String, ValueColor> data = new HashMap<String, ValueColor>();


    public TemplateMsgDO(String touser, String template_id, String url) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
    }

    public TemplateMsgDO() {
    }

    protected boolean verifyKey(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (key.equalsIgnoreCase(FIRST_KEY) || key.equalsIgnoreCase(REMARK_KEY)) {
            return false;
        }

        return true;
    }


    public static class ValueColor {
        private String value;
        private String color;

        public ValueColor(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "ValueColor{" +
                    "value='" + value + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFirst(ValueColor first) {
        data.put(FIRST_KEY, first);
    }

    public void setRemark(ValueColor remark) {
        data.put(REMARK_KEY, remark);
    }

    public void put(String key, ValueColor valueColor) {
        if (verifyKey(key)) {
            data.put(key, valueColor);
        }
    }

    public Map<String, ValueColor> getData() {
        return data;
    }


}
