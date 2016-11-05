package com.line.bqxd.platform.common.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/5/21.
 */
public class ParameterTool {

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL().substring(0,
                request.getRequestURL().indexOf(request.getContextPath()) + request.getContextPath().length()) + "/";
    }

    public static String getFullUrl(String url, HttpServletRequest request){
        if (StringUtils.isBlank(url)) {
            return getBaseUrl(request);
        }
        LinkTool linkTool = new LinkTool(url);
        String pfAppid = request.getParameter("pfAppid");
        if (StringUtils.isNotBlank(pfAppid)) {
            linkTool.setParamIfAbsent("pfAppid", pfAppid);
        }

        String concurPlanId = request.getParameter("concurPlanId");
        if (StringUtils.isNotBlank(concurPlanId)) {
            linkTool.setParamIfAbsent("concurPlanId", concurPlanId);

        }
        return linkTool.toString();
    }

    public static String getAppUrl(String url, HttpServletRequest request){
        if (StringUtils.isBlank(url)) {
            return getBaseUrl(request);
        }
        LinkTool linkTool = new LinkTool(url);
        String pfAppid = request.getParameter("pfAppid");
        if (StringUtils.isNotBlank(pfAppid)) {
            linkTool.setParamIfAbsent("pfAppid", pfAppid);
        }
        return linkTool.toString();
    }

    public static String getActivityUrl(String url, HttpServletRequest request) {
        if (StringUtils.isBlank(url)) {
            return getBaseUrl(request);
        }
        LinkTool linkTool = new LinkTool(url);
        String activityId = request.getParameter("activityId");
        String shareTextId = request.getParameter("shareTextId");
        String channelId = request.getParameter("channelId");
        //分享的层次
        String shareLevel = request.getParameter("shareLevel");
        String shareUserId = request.getParameter("shareUserId");

        String concurPlanId = request.getParameter("concurPlanId");
        String concurPlanGroupId = request.getParameter("concurPlanGroupId");

        String f = request.getParameter("f");

        /*
        String[] arrSplit = url.split("[?]");
        StringBuilder sb = new StringBuilder(50);
        sb.append(arrSplit[0]);
        sb.append("?");

        StringBuilder paramSb = new StringBuilder();
        */
        if (StringUtils.isNotBlank(activityId)) {
            linkTool.setParamIfAbsent("activityId", activityId);

        }
        if (StringUtils.isNotBlank(channelId)) {
            linkTool.setParamIfAbsent("channelId", channelId);

        }
        if (StringUtils.isNotBlank(shareTextId)) {
            linkTool.setParamIfAbsent("shareTextId", shareTextId);
        }
        if (StringUtils.isNotBlank(shareLevel)) {
            linkTool.setParamIfAbsent("shareLevel", shareLevel);
        }
        if (StringUtils.isNotBlank(shareUserId)) {
            linkTool.setParamIfAbsent("shareUserId", shareUserId);
        }
        if (StringUtils.isNotBlank(concurPlanId)) {
            linkTool.setParamIfAbsent("concurPlanId", concurPlanId);
        }
        if (StringUtils.isNotBlank(concurPlanGroupId)) {
            linkTool.setParamIfAbsent("concurPlanGroupId", concurPlanGroupId);
        }
        if (StringUtils.isNotBlank(f)) {
            linkTool.setParamIfAbsent("f", f);
        }
        /*
        if (arrSplit.length > 1) {
            paramSb.append('&').append(arrSplit[1]);
        }
        String parms = paramSb.toString();
        if (StringUtils.isNotBlank(parms)) {
            return arrSplit[0] + "?" + parms;
        } else {
            return arrSplit[0];
        }
        */
        String rtUrl = linkTool.toString();
        //微信ANDROID坑
        if (!rtUrl.contains("?")) {
            rtUrl += "?1=1";
        }else{
            rtUrl += "&1=1";
        }

        return rtUrl;
    }

    public static String getActivityUrlWithShare(String url, HttpServletRequest request) {
        if (StringUtils.isBlank(url)) {
            return getBaseUrl(request);
        }
        LinkTool linkTool = new LinkTool(url);
        String activityId = request.getParameter("activityId");
        String shareTextId = request.getParameter("shareTextId");
        String channelId = request.getParameter("channelId");
        //分享的层次
        String shareLevel = request.getParameter("shareLevel");
        String shareUserId = request.getParameter("shareUserId");

        String concurPlanId = request.getParameter("concurPlanId");
        String concurPlanGroupId = request.getParameter("concurPlanGruopId");

        String f = request.getParameter("f");

        /*
        String[] arrSplit = url.split("[?]");
        StringBuilder sb = new StringBuilder(50);
        sb.append(arrSplit[0]);
        sb.append("?");

        StringBuilder paramSb = new StringBuilder();
        */
        if (StringUtils.isNotBlank(activityId)) {
            linkTool.setParamIfAbsent("activityId", activityId);

        }
        if (StringUtils.isNotBlank(channelId)) {
            linkTool.setParamIfAbsent("channelId", channelId);

        }
        if (StringUtils.isNotBlank(shareTextId)) {
            linkTool.setParamIfAbsent("shareTextId", shareTextId);
        }
        if (StringUtils.isNotBlank(shareLevel)) {
            linkTool.setParamIfAbsent("shareLevel", shareLevel);
        }
        if (StringUtils.isNotBlank(shareUserId)) {
            linkTool.setParamIfAbsent("shareUserId", shareUserId);
        } else {
            linkTool.setParamIfAbsent("shareUserId", "68");

        }
        if (StringUtils.isNotBlank(concurPlanId)) {
            linkTool.setParamIfAbsent("concurPlanId", concurPlanId);
        }
        if (StringUtils.isNotBlank(concurPlanGroupId)) {
            linkTool.setParamIfAbsent("concurPlanGroupId", concurPlanGroupId);
        }
        if (StringUtils.isNotBlank(f)) {
            linkTool.setParamIfAbsent("f", f);
        }
        /*
        if (arrSplit.length > 1) {
            paramSb.append('&').append(arrSplit[1]);
        }
        String parms = paramSb.toString();
        if (StringUtils.isNotBlank(parms)) {
            return arrSplit[0] + "?" + parms;
        } else {
            return arrSplit[0];
        }
        */
        return linkTool.toString();
    }
}
