package com.line.bqxd.platform.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/6/10.
 */
public class UrlTool {

    public static String getAbsoluteUrl(HttpServletRequest request, String uri){
        StringBuilder sb= new StringBuilder();
        String serverName=request.getServerName();
        int port=request.getServerPort();
        String scheme=request.getScheme();
        sb.append(scheme);
        sb.append("://");
        sb.append(serverName);
        if(port!=80){
            sb.append(":");
            sb.append(port);
        }
        sb.append(uri);
        return sb.toString();
    }

}
