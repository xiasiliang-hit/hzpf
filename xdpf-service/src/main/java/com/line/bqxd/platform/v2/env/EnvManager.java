package com.line.bqxd.platform.v2.env;

import com.line.bqxd.platform.common.HttpResult;
import com.line.bqxd.platform.common.HttpRetryHandle;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.manager.RunEnv;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;

/**
 * Created by huangjianfei on 16/9/7.
 */
public class EnvManager {
    private static final String WEB_SERVICE_IP = "10.251.175.72,10.144.225.16";

    private static final String DEV_PF_GET_DATA_URL = "http://139.129.58.215:8788/env/getData.htm?type=";

    private static final String ONLINE_PF_GET_DATA_URL = "http://115.28.153.35:8788/env/getData.htm?type=";

    private static final String DEV_PF_GET_APP_DATA_URL = "http://139.129.58.215:8788/env/getData.htm?appid=";

    private static final String ONLINE_PF_GET_APP_DATA_URL = "http://115.28.153.35:8788/env/getData.htm?appid=";

    private static final String LOCAL_IP = getLocalIp();

    @Autowired
    private HttpClient bqxdHttpClient;

    @Autowired
    private HttpRetryHandle bqxdHttpRetryHandle;

    @Autowired
    private RunEnv runEnv;

    public static String getLocalIp() {
        InetAddress ia = null;
        try {
            ia = ia.getLocalHost();
            String localip = ia.getHostAddress();
            return localip;
        } catch (Exception e) {

        }
        return null;
    }

    public boolean isStartPfData() {
        if (StringUtils.isBlank(LOCAL_IP)) {
            return true;
        }
        if (WEB_SERVICE_IP.indexOf(LOCAL_IP) >= 0) {
            return true;
        }
        return false;
    }

    public boolean isRemoteData() {
        return !isStartPfData();
    }

    public String getData(String type) throws Exception {
        HttpResult result = null;
        if (StringUtils.isBlank(type)) {
            return null;
        }
        if ("componentVerifyTicket".equals(type) || "componentAccessToken".equals(type) || "preAuthCode".equals(type)) {
            if (runEnv.isOnline()) {
                result = HttpUtil.sendBrowser(bqxdHttpClient, bqxdHttpRetryHandle, ONLINE_PF_GET_DATA_URL + type, "UTF-8");
            } else {
                result = HttpUtil.sendBrowser(bqxdHttpClient, bqxdHttpRetryHandle, DEV_PF_GET_DATA_URL + type, "UTF-8");
            }
            if (result != null && result.isSuccess()) {
                return result.getContent();
            }
        } else {

        }
        return null;

    }

    public String getAppData(String appid,String type) throws Exception {
        HttpResult result = null;
        if (StringUtils.isBlank(appid)||StringUtils.isBlank(type)) {
            return null;
        }
        if ("accessToken".equals(type) || "ticket".equals(type) ) {
            if (runEnv.isOnline()) {
                result = HttpUtil.sendBrowser(bqxdHttpClient, bqxdHttpRetryHandle, ONLINE_PF_GET_APP_DATA_URL + appid+"&type="+type, "UTF-8");
            } else {
                result = HttpUtil.sendBrowser(bqxdHttpClient, bqxdHttpRetryHandle, DEV_PF_GET_APP_DATA_URL + appid+"&type="+type, "UTF-8");
            }
            if (result != null && result.isSuccess()) {
                return result.getContent();
            }
        } else {

        }
        return null;

    }


}
