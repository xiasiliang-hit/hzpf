package com.line.bqxd.platform.v2;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.common.HttpRetryHandle;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.utils.FileUtils;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.v2.auth.Constant;
import com.line.bqxd.platform.v2.env.EnvManager;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangjianfei on 16/9/1.
 */
public class PfApplication implements Constant {

    private static Logger logger = LoggerFactory.getLogger(PfApplication.class);

    private volatile String componentVerifyTicket = null;

    private String componentAccessToken = null;

    private String preAuthCode = null;

    private String token = null;

    private String encodingAesKey = null;

    private String pfAppId = null;

    private String pfAppsecret = null;

    private Map<String,GlobalObject> globalObjectMap = new HashMap<String,GlobalObject>();

    @Autowired
    private HttpClient bqxdHttpClient;

    @Autowired
    private HttpRetryHandle bqxdHttpRetryHandle;

    @Autowired
    private EnvManager envManager;

    private String configFile="";

    private volatile boolean preAuthCodeThread = false;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "weixinPfAccessTokenThread");
                }
            });


    public synchronized void init() throws Exception {

    }
    public void start()throws Exception {
        String content= FileUtils.readFileByLines(configFile);
        if (StringUtils.isNotBlank(content)) {
            String[] configs = content.split(";");
            if (configs != null && configs.length == 2) {
                preAuthCode = configs[0];
                componentAccessToken = configs[1];
            } else {
                logger.warn("PfApplication start fail,configs={}", configs);
            }
        }
    }

    public synchronized void destory() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {

            scheduledExecutorService.shutdown();
        }
    }


    public void setComponentVerifyTicket(String componentVerifyTicket) {
        boolean isfirst=false;
        if(StringUtils.isBlank(this.componentVerifyTicket)){
            isfirst=true;
        }
        this.componentVerifyTicket = componentVerifyTicket;

        if(isfirst){
            //需要重新启动获取component_access_token
            scheduledComponentAccessToken();
        }
    }

    public String getComponentVerifyTicket() {
        if(envManager.isRemoteData()){
            try {
                return envManager.getData("componentVerifyTicket");
            } catch (Exception e) {
               return null;
            }
        }else {
            return componentVerifyTicket;
        }
    }

    public String getComponentAccessToken() {
        if(envManager.isRemoteData()){
            try {
                return envManager.getData("componentAccessToken");
            } catch (Exception e) {
                return null;
            }
        }else {
            return componentAccessToken;
        }
    }

    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }

    public String getPreAuthCode() {
        if(envManager.isRemoteData()){
            try {
                return envManager.getData("preAuthCode");
            } catch (Exception e) {
                return null;
            }
        }else {
            return preAuthCode;
        }
    }

    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }


    public void setBqxdHttpClient(HttpClient bqxdHttpClient) {
        this.bqxdHttpClient = bqxdHttpClient;
    }

    public void setBqxdHttpRetryHandle(HttpRetryHandle bqxdHttpRetryHandle) {
        this.bqxdHttpRetryHandle = bqxdHttpRetryHandle;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public void setPfAppId(String pfAppId) {
        this.pfAppId = pfAppId;
    }

    public void setPfAppsecret(String pfAppsecret) {
        this.pfAppsecret = pfAppsecret;
    }

    public String getToken() {
        return token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public String getPfAppId() {
        return pfAppId;
    }

    public String getPfAppsecret() {
        return pfAppsecret;
    }

    private void scheduledComponentAccessToken(){
        int verifyTicketPeriod = 7200 - 1200;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    //PF_ACCESS_TOKEN_URL
                    logger.warn("schedule get component access token begin,componentVerifyTicket={}", componentVerifyTicket);
                    JSONObject jsonObject = HttpUtil.conver(HttpUtil.sendOriginally(PF_ACCESS_TOKEN_URL,builderComponentVerifyTicketPostData(),"utf-8"));
                    if (jsonObject != null) {
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get component access token jsonObject,{}",jsonObject);
                        }
                        String value=jsonObject.getString("component_access_token");
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get component access token value={}",value);
                        }
                        if(StringUtils.isNotBlank(value)){
                            componentAccessToken = value;
                            //由于采用单个线程,所以不需要同步
                            FileUtils.writeFile(preAuthCode+";"+componentAccessToken,configFile);
                        }
                        if(!preAuthCodeThread){
                            scheduledPreAuthCode();
                            logger.warn("start execute per auth code thread");
                        }
                        logger.warn("schedule component access token end success,componentAccessToken={}", componentAccessToken);

                    } else {
                        logger.warn("schedule component access token end,jsonObject is null,{}",componentAccessToken);
                    }
                } catch (Exception e) {
                    logger.error("schedule get component access token fail", e);
                } finally {
                    logger.warn("schedule get component access token end,{}", componentAccessToken);
                }
            }
        }, 1, verifyTicketPeriod, TimeUnit.SECONDS);


    }

    private void scheduledPreAuthCode(){
        int preAuthCodePeriod = 600-60;
        preAuthCodeThread=true;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(StringUtils.isBlank(componentAccessToken)){
                    logger.warn("schedule get per auth code,componentAccessToken is blank");

                    return;
                }
                try {
                    //PF_ACCESS_TOKEN_URL
                    logger.warn("schedule get per auth code begin,perAuthCode={}", preAuthCode);
                    JSONObject jsonObject = HttpUtil.conver(HttpUtil.sendOriginally(PF_PRE_AUTH_CODE_URL+componentAccessToken,builderPreAuthCodePostData(),"utf-8"));
                    if (jsonObject != null) {
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get per auth code jsonObject,{}",jsonObject);
                        }
                        preAuthCode=(jsonObject.getString("pre_auth_code"));
                        //由于采用单个线程,所以不需要同步
                        FileUtils.writeFile(preAuthCode+";"+componentAccessToken,configFile);
                        //globalObject.setExpiresIn(jsonObject.getInteger("expires_in"));
                        logger.warn("schedule per auth code end success,preAuthCode={}", preAuthCode);

                    } else {
                        logger.warn("schedule per auth code end,jsonObject is null,{}",preAuthCode);
                    }
                } catch (Exception e) {
                    logger.error("schedule get per auth code fail", e);
                } finally {
                    logger.warn("schedule get per auth code end,{}", preAuthCode);
                }
            }
        }, 1, preAuthCodePeriod, TimeUnit.SECONDS);
    }

    private String builderComponentVerifyTicketPostData() {
        StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("\"component_appid\":\"" + pfAppId + "\" ,");
        sb.append("\"component_appsecret\": \"" + pfAppsecret + "\", ");
        sb.append("\"component_verify_ticket\": \"" + componentVerifyTicket + "\" ");
        sb.append('}');
        return sb.toString();

    }

    private String builderPreAuthCodePostData() {
        StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("\"component_appid\":\"" + pfAppId + "\"");
        sb.append('}');
        return sb.toString();

    }

    public Map<String, GlobalObject> getGlobalObjectMap() {
        return globalObjectMap;
    }

    public void setGlobalObjectMap(Map<String, GlobalObject> globalObjectMap) {
        this.globalObjectMap = globalObjectMap;
    }

    public GlobalObject getGlobalObjectByAppid(String authAppid) {
        if (StringUtils.isBlank(authAppid)) {
            return null;
        }
        return globalObjectMap.get(authAppid);
    }
    public String getJSTicketByAppid(String authAppid){
        if (StringUtils.isBlank(authAppid)) {
            return null;
        }
        if(envManager.isRemoteData()){
            try {
                return envManager.getAppData(authAppid,"ticket");
            } catch (Exception e) {
                return null;
            }
        }else {
            return globalObjectMap.get(authAppid).getTicket();
        }
    }

    public String getAccessTokenByAppid(String authAppid){
        if (StringUtils.isBlank(authAppid)) {
            return null;
        }
        if(envManager.isRemoteData()){
            try {
                return envManager.getAppData(authAppid,"accessToken");
            } catch (Exception e) {
                return null;
            }
        }else {
            return globalObjectMap.get(authAppid).getAccessToken();
        }
    }
    public void addGlobalObject(GlobalObject globalObject) {
        globalObjectMap.put(globalObject.getAppId(), globalObject);
    }

    public JSONObject getObject(String url) throws Exception {
        return HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, url);
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
