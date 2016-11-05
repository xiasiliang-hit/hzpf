package com.line.bqxd.platform.v2.manager.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.client.dataobject.query.PfWeixinAuthQueryDO;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.dao.PfWeixinAuthDAO;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.env.EnvManager;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangjianfei on 16/9/6.
 */
public class PfWeixinAuthManagerImpl implements PfWeixinAuthManager {
    private static final Logger logger = LoggerFactory.getLogger(PfWeixinAuthManagerImpl.class);

    @Resource  //自动注入,默认按名称
    private PfWeixinAuthDAO<PfWeixinAuthDO> pfWeixinAuthDAO;

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private EnvManager envManager;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "weixinAuthorizerAccessTokenThread");
                }
            });

    public synchronized void init()throws Exception {
        pfApplication.start();
        startAuth();
    }

    @Override
    public void startAuth() {
        if (envManager.isStartPfData()) {
            List<PfWeixinAuthDO> list = getAll();
            if (list.isEmpty()) {
                return;
            }
            for (PfWeixinAuthDO pfWeixinAuthDO : list) {
                GlobalObject globalObject = getGlobalObject(pfWeixinAuthDO);
                pfApplication.addGlobalObject(globalObject);
                addScheduledAuthorizerAccessToken(pfWeixinAuthDO.getAuthorizerAppid(), true);
            }
        } else {
            logger.warn("develop evn not start auth");
        }
    }

    @Override
    public boolean insert(PfWeixinAuthDO pfWeixinAuthDO) {
        long rt = pfWeixinAuthDAO.insert(pfWeixinAuthDO);
        return rt > 0 ? true : false;
    }

    @Override
    public boolean update(PfWeixinAuthDO pfWeixinAuthDO) {
        return pfWeixinAuthDAO.update(pfWeixinAuthDO);
    }

    @Override
    public boolean updateByAppId(PfWeixinAuthDO pfWeixinAuthDO) {
        return pfWeixinAuthDAO.updateByAppId(pfWeixinAuthDO);
    }

    @Override
    public List<PfWeixinAuthDO> getAll() {
        //前期业务较少,用户少于1000,等大于1000的时候需要修改
        PfWeixinAuthQueryDO pfWeixinAuthQueryDO = new PfWeixinAuthQueryDO();
        pfWeixinAuthQueryDO.setPageSize(1000);
        pfWeixinAuthQueryDO.setPageNum(1);
        return pfWeixinAuthDAO.selectByQuery(pfWeixinAuthQueryDO);
    }

    @Override
    public PfWeixinAuthDO builderDO(JSONObject queryAuthJson, JSONObject authInfoJson) {
        if (queryAuthJson == null || authInfoJson == null) {
            return null;
        }
        PfWeixinAuthDO pfWeixinAuthDO = new PfWeixinAuthDO();
        JSONObject jsonObject1 = queryAuthJson.getJSONObject("authorization_info");
        if (jsonObject1 == null) {
            return null;
        }
        pfWeixinAuthDO.setAuthorizerAppid(jsonObject1.getString("authorizer_appid"));
        pfWeixinAuthDO.setAuthorizerAccessToken(jsonObject1.getString("authorizer_access_token"));
        pfWeixinAuthDO.setAuthorizerRefreshToken(jsonObject1.getString("authorizer_refresh_token"));
        pfWeixinAuthDO.setExpiresIn(jsonObject1.getIntValue("expires_in"));
        pfWeixinAuthDO.setFuncInfo(getFuncInfo(jsonObject1.getJSONArray("func_info")));

        JSONObject jsonObject2 = authInfoJson.getJSONObject("authorizer_info");
        if (jsonObject2 == null) {
            return null;
        }
        pfWeixinAuthDO.setNickName(jsonObject2.getString("nick_name"));
        pfWeixinAuthDO.setUserName(jsonObject2.getString("user_name"));
        pfWeixinAuthDO.setHeadImg(jsonObject2.getString("head_img"));
        pfWeixinAuthDO.setServiceTypeInfo(jsonObject2.getJSONObject("service_type_info").getInteger("id"));
        pfWeixinAuthDO.setVerifyTypeInfo(jsonObject2.getJSONObject("verify_type_info").getInteger("id"));
        pfWeixinAuthDO.setBusinessInfo(jsonObject2.getString("business_info"));
        pfWeixinAuthDO.setAlias(jsonObject2.getString("alias"));
        pfWeixinAuthDO.setQrcodeUrl(authInfoJson.getString("qrcode_url"));
        return pfWeixinAuthDO;
    }

    @Override
    public void addScheduledAuthorizerAccessToken(final String appid, final boolean isImmediately) {
        //int verifyTicketPeriod = 3600;
        int verifyTicketPeriod = 600;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    //PF_ACCESS_TOKEN_URL
                    logger.warn("schedule get AuthorizerAccessToken begin,appId={}", appid);
                    PfWeixinAuthDO pfWeixinAuthDO = selectByAppid(appid);
                    if (pfWeixinAuthDO != null) {
                        String postData=buiderApiAuthorizerTokenPostData(pfWeixinAuthDO);
                        String url=buiderApiAuthorizerTokenUrl();
                        if(logger.isDebugEnabled()){
                            logger.debug("schedule get AuthorizerAccessToken url={},postData={}",url,postData);
                        }
                        JSONObject authorizerTokenJson = HttpUtil.conver(HttpUtil.sendOriginally(url, postData, "utf-8"));
                        if (authorizerTokenJson != null && StringUtils.isNotBlank(authorizerTokenJson.getString("authorizer_access_token"))) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("schedule get AuthorizerAccessToken refresh {}", authorizerTokenJson);
                            }
                            //更新数据库中的值
                            PfWeixinAuthDO update = new PfWeixinAuthDO();
                            update.setId(pfWeixinAuthDO.getId());
                            update.setAuthorizerAppid(pfWeixinAuthDO.getAuthorizerAppid());
                            String accessToken = authorizerTokenJson.getString("authorizer_access_token");
                            String refreshToken = authorizerTokenJson.getString("authorizer_refresh_token");
                            update.setAuthorizerAccessToken(accessToken);
                            update.setAuthorizerRefreshToken(refreshToken);

                            update.setExpiresIn(authorizerTokenJson.getIntValue("expires_in"));
                            JSONObject jsJsonObject = pfApplication.getObject(Application.TICKET_URL + pfWeixinAuthDO.getAuthorizerAccessToken());
                            if (logger.isDebugEnabled()) {
                                logger.debug("schedule get AuthorizerAccessToken jsticket={}", jsJsonObject);
                            }
                            String jsTicket = null;
                            if (jsJsonObject != null) {
                                jsTicket = (jsJsonObject.getString("ticket"));
                                update.setJsTicket(jsTicket);
                            }

                            pfWeixinAuthDAO.update(update);
                            //设置内存保持
                            GlobalObject globalObject = pfApplication.getGlobalObjectByAppid(pfWeixinAuthDO.getAuthorizerAppid());
                            globalObject.setAccessToken(accessToken);
                            globalObject.setRefreshToken(refreshToken);
                            globalObject.setTicket(jsTicket);
                            if (logger.isDebugEnabled()) {
                                logger.debug("schedule get AuthorizerAccessToken execute {}", update);
                            }
                        } else {
                            logger.error("schedule get AuthorizerAccessToken fail,remote data not exits,appid={},{}", appid, authorizerTokenJson);
                        }
                    } else {
                        logger.error("schedule get AuthorizerAccessToken fail,data not exits,appid={}", appid);
                    }
                } catch (Exception e) {
                    logger.error("schedule get AuthorizerAccessToken fail", e);
                } finally {
                    logger.warn("schedule get AuthorizerAccessToken end,{}", appid);
                }
            }
        }, isImmediately ? 1 : verifyTicketPeriod, verifyTicketPeriod, TimeUnit.SECONDS);
    }

    @Override
    public PfWeixinAuthDO selectByAppid(String appid) {
        return pfWeixinAuthDAO.selectByAppid(appid);
    }

    @Override
    public PfWeixinAuthDO selectByCorpid(long corpid) {

        PfWeixinAuthQueryDO pfWeixinAuthQueryDO = new PfWeixinAuthQueryDO();
        pfWeixinAuthQueryDO.setPageSize(1000);
        pfWeixinAuthQueryDO.setPageNum(1);
        pfWeixinAuthQueryDO.setCorpId(corpid);
        List<PfWeixinAuthDO> list = pfWeixinAuthDAO.selectByQuery(pfWeixinAuthQueryDO);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public PfWeixinAuthDO selectByid(long id) {
        return (PfWeixinAuthDO) pfWeixinAuthDAO.selectById(id);
    }

    private String getFuncInfo(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (Object object : jsonArray) {
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                Integer id = jsonObject.getJSONObject("funcscope_category").getInteger("id");
                list.add(id);
            }
        }
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer arg0, Integer arg1) {
                return arg0 > arg1 ? 1 : -1;
            }
        });
        return JSONObject.toJSONString(list);
    }

    public synchronized void destory() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {

            scheduledExecutorService.shutdown();
        }
    }


    private String buiderApiAuthorizerTokenUrl() {
        StringBuilder sb = new StringBuilder(PfApplication.PF_AUTHORIZER_REFRESH_TOKEN_URL);
        sb.append(pfApplication.getComponentAccessToken());

        return sb.toString();
    }

    private String buiderApiAuthorizerTokenPostData(PfWeixinAuthDO pfWeixinAuthDO) {
        StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("\"component_appid\":\"" + pfApplication.getPfAppId() + "\",");
        sb.append("\"authorizer_appid\":\"" + pfWeixinAuthDO.getAuthorizerAppid() + "\",");
        sb.append("\"authorizer_refresh_token\":\"" + pfWeixinAuthDO.getAuthorizerRefreshToken() + "\"");
        sb.append('}');
        return sb.toString();

    }

    public GlobalObject getGlobalObject(PfWeixinAuthDO pfWeixinAuthDO) {
        GlobalObject globalObject = new GlobalObject();
        globalObject.setAccessToken(pfWeixinAuthDO.getAuthorizerAccessToken());
        globalObject.setRefreshToken(pfWeixinAuthDO.getAuthorizerRefreshToken());
        globalObject.setExpiresIn(pfWeixinAuthDO.getExpiresIn());
        globalObject.setAppId(pfWeixinAuthDO.getAuthorizerAppid());
        globalObject.setTicket(pfWeixinAuthDO.getJsTicket());
        globalObject.setPfAppId(pfWeixinAuthDO.getId());
        return globalObject;
    }

}
