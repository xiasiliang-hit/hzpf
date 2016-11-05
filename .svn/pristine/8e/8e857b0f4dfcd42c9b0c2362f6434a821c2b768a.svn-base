package com.line.bqxd.platform.manager.wxpay.utils;

import com.line.bqxd.platform.manager.wxpay.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class PayUtil {
    public static String join(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.toString();

    }

    public static String createWXPaySign(String charSet, SortedMap<Object, Object> parameters, String paySecret) {
        String joinStr = join(parameters);
        joinStr += "key=" + paySecret;
        String sign = MD5Util.MD5Encode(joinStr, charSet).toUpperCase();
        return sign;
    }

    public static String createSign(String charSet, SortedMap<Object, Object> parameters) {
        String joinStr = join(parameters);
        String sign = MD5Util.MD5Encode(joinStr, charSet).toUpperCase();
        return sign;
    }

    public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

        if (null == request || null == response) {
            return "gbk";
        }

        String enc = request.getCharacterEncoding();

        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }

        return enc;

    }

    public static void main(String[] args){


    }
}
