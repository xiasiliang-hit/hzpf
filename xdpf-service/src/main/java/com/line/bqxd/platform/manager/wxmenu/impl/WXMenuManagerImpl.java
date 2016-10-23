package com.line.bqxd.platform.manager.wxmenu.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.common.WXBase;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.HttpResult;
import com.line.bqxd.platform.common.HttpRetryHandle;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.wxmenu.WXMenuManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by huangjianfei on 16/5/4.
 */
public class WXMenuManagerImpl implements WXMenuManager {

    private static Logger logger = LoggerFactory.getLogger(WXMenuManagerImpl.class);

    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    private static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";


    @Autowired
    private GlobalObject globalObject;

    @Autowired
    private HttpClient bqxdHttpClient;

    @Autowired
    private HttpRetryHandle bqxdHttpRetryHandle;

    @Override
    public BizResult<Boolean> createMenu(Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            return createMenu(JSON.toJSONString(map));
        } else {
            return new BizResult(ResultEnum.ARGUMENT_VERIFY_FAIL);
        }
    }

    @Override
    public BizResult<Boolean> createMenu(String menu) {
        if (StringUtils.isNotBlank(menu)) {
            try {
                Map<String, Object> map = JSON.parseObject(menu, Map.class);
                if (CollectionUtils.isEmpty(map)) {
                    return new BizResult(ResultEnum.ARGUMENT_VERIFY_FAIL);
                }
                //JSON.parseObject()
            } catch (Exception e) {
                logger.error("json parse createMenu error", e);
                return new BizResult(ResultEnum.SYSERROR);
            }
            String url = CREATE_MENU_URL + globalObject.getAccessToken();
            try {
                HttpResult result = HttpUtil.sendOriginally(url, menu, "UTF-8");
                if (result == null || !result.isSuccess()) {
                    logger.warn("createMenu result,false {}", result == null ? null : result);
                    BizResult bizResult = new BizResult(ResultEnum.SYSERROR);
                    if (result == null) {
                        bizResult.setDetailInfo("null");
                    } else {
                        bizResult.setDetailInfo(result.getContent());
                    }
                    return bizResult;
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("createMenu request success {}", result);
                    }
                    JSONObject jsonObject=JSONObject.parseObject(result.getContent());
                    if(jsonObject.getString("errcode").equals("0")&&jsonObject.getString("errmsg").equals("ok")) {
                        return new BizResult(ResultEnum.SUCCESS);
                    }else{
                        BizResult bizResult=new BizResult(ResultEnum.SYSERROR);
                        bizResult.setDetailInfo(result.getContent());
                        return bizResult;
                    }
                }
            } catch (Exception e) {
                logger.error("createMenu error", e);
                return new BizResult(ResultEnum.SYSERROR);
            }
        } else {
            return new BizResult(ResultEnum.ARGUMENT_VERIFY_FAIL);
        }

    }

    @Override
    public void deleteMenu() {
        String url = DELETE_MENU_URL + globalObject.getAccessToken();
        try {
            WXBase result = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, url, WXBase.class);
            if (result == null || result.getErrcode() != 0) {
                logger.warn("deleteMenu result fail, {}", result == null ? null : result);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("deleteMenu result success, {}", result);
                }
            }
        } catch (Exception e) {
            logger.error("deleteMenu error", e);
        }
    }

    @Override
    public String getMenu() {
        String url = GET_MENU_URL + globalObject.getAccessToken();
        try {
            HttpResult result = HttpUtil.send(bqxdHttpClient, bqxdHttpRetryHandle, url, "UTF-8");
            //HttpResult result = HttpUtil.sendOriginally(url, null, "UTF-8");
            if (result == null || !result.isSuccess()) {
                logger.warn("getMenu result,false {}", result == null ? null : result);
                return null;
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("createMenu result success {}", result);
                }
                return result.getContent();
            }
        } catch (Exception e) {
            logger.error("createMenu error", e);
            return null;
        }
    }
}
