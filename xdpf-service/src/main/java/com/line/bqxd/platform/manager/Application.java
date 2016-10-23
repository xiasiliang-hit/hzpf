package com.line.bqxd.platform.manager;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.common.HttpRetryHandle;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.dataobject.GlobalObject;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangjianfei on 16/4/21.
 */
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private String appId = null;
    private String appSecret = null;
    private static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
    private String appToken = null;
    @Autowired
    private HttpClient bqxdHttpClient;

    @Autowired
    private HttpRetryHandle bqxdHttpRetryHandle;

    @Autowired
    private GlobalObject globalObject;

    private String defaultShareImgUrl;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "weixinAccessTokenThread");
                }
            });

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppSecret() {
        return appSecret;
    }


    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public synchronized void init() throws Exception {
        StringBuilder sb = new StringBuilder(URL);
        sb.append("&");
        sb.append("appid=");
        sb.append(appId);
        sb.append("&");
        sb.append("secret=");
        sb.append(appSecret);
        final String allUrl = sb.toString();
        //提早10分钟执行
        int period = 7200 - 600;
        //int period=600;
        /**
        try {
            logger.warn("first get ticket begin,{}", globalObject);
            JSONObject jsonObject = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, allUrl);
            if (jsonObject != null) {
                globalObject.setAccessToken(jsonObject.getString("access_token"));
                globalObject.setExpiresIn(jsonObject.getInteger("expires_in"));
                period = globalObject.getExpiresIn() - 600;
                JSONObject jsJsonObject = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, TICKET_URL + globalObject.getAccessToken());
                if (jsJsonObject != null) {
                    globalObject.setTicket(jsJsonObject.getString("ticket"));
                } else {
                    logger.warn("first get ticket end,jsJsonObject is null,{}", globalObject);
                }

            } else {
                logger.warn("first get ticket end,jsonObject is null,{}", globalObject);
            }
        } catch (Exception e) {
            logger.error("get ticket error", e);
        } finally {
            logger.warn("first get ticket end,{}", globalObject);
        }

         **/
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.warn("schedule get ticket begin,", globalObject);
                    JSONObject jsonObject = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, allUrl);
                    if (jsonObject != null) {
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get ticket jsonObject,{}",jsonObject);
                        }
                        globalObject.setAccessToken(jsonObject.getString("access_token"));
                        globalObject.setExpiresIn(jsonObject.getInteger("expires_in"));
                        JSONObject jsJsonObject = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, TICKET_URL + globalObject.getAccessToken());
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get ticket jsJsonObject,{}",jsJsonObject);
                        }
                        if (jsJsonObject != null) {
                            globalObject.setTicket(jsJsonObject.getString("ticket"));
                        } else {
                            logger.warn("schedule get ticket end,jsJsonObject is null,{}", globalObject);
                        }
                    } else {
                        logger.warn("schedule get ticket end,jsonObject is null,{}", globalObject);
                    }
                } catch (Exception e) {
                    logger.error("schedule get ticket fail", e);
                } finally {
                    logger.warn("schedule get ticket end,{}", globalObject);
                }
            }
        }, 1, period, TimeUnit.SECONDS);

    }


    public JSONObject getObject(String url) throws Exception {
        return HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, url);
    }


    public String getDefaultShareImgUrl() {
        return defaultShareImgUrl;
    }

    public void setDefaultShareImgUrl(String defaultShareImgUrl) {
        this.defaultShareImgUrl = defaultShareImgUrl;
    }

    public synchronized void destory() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {

            scheduledExecutorService.shutdown();
        }
    }


}
