package com.line.bqxd.platform.manager.wxpay;

import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class RequestHandler {

    private SortedMap parameters = new TreeMap();

    /**
     * 获取参数值
     * @param parameter 参数名称
     * @return String
     */

    public String getParameter(String parameter) {

        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;

    }

    /**
     * 设置参数值
     * @param parameter      参数名称
     * @param parameterValue 参数值
     */

    public void setParameter(String parameter, String parameterValue) {
        String v = "";

        if (null != parameterValue) {
            v = parameterValue.trim();
        }

        this.parameters.put(parameter, v);

    }


    public String getRequestURL(String payScert, String charSet) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(charSet)){
            charSet="UTF-8";
        }
        String sign = PayUtil.createWXPaySign(charSet, this.parameters, payScert);

        this.setParameter("sign", sign);

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key) || "notify_url".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }

        sb.append("</xml>");

        return sb.toString();

    }

    public SortedMap getParameters() {
        return parameters;
    }
}
