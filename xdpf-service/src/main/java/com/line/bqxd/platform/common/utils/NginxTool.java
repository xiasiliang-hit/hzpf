package com.line.bqxd.platform.common.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/5/23.
 */
public class NginxTool {

    public static String getIpByNginx(HttpServletRequest req){
        String ip=req.getHeader("X-Real-IP");
        if(StringUtils.isBlank(ip)){
            ip=req.getRemoteAddr();
        }
        return ip;
    }
}
