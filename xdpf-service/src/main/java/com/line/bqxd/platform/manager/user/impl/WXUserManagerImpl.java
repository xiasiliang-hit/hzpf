package com.line.bqxd.platform.manager.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.common.utils.NginxTool;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.manager.user.WXUserManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by huangjianfei on 16/8/4.
 */
public class WXUserManagerImpl implements WXUserManager{
    private static Logger logger = LoggerFactory.getLogger(WXUserManagerImpl.class);


    private static final String AUTH_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";

    private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN";


    @Autowired
    private Application application;

    @Override
    public UserDO getSynUserInfo(String accessToken, String openid,boolean isAuth) throws Exception{
        String url=getUserInfoUrl(accessToken,openid,isAuth);
        JSONObject userJsonObject = application.getObject(url);
        if(userJsonObject==null){
            userJsonObject = application.getObject(url);
        }else{
            String unionid=userJsonObject.getString("unionid");
            logger.warn("getSynUserInfo first get unionid is null");
            if(StringUtils.isBlank(unionid)){
                userJsonObject=application.getObject(url);
            }
        }
        if(StringUtils.isBlank(userJsonObject.getString("unionid"))){
            logger.warn("getSynUserInfo is unusual {} ",userJsonObject);
        }
        UserDO userDO = new UserDO();
        userDO.setNickName(userJsonObject.getString("nickname"));
        userDO.setSex(userJsonObject.getIntValue("sex"));
        userDO.setCity(userJsonObject.getString("city"));
        userDO.setProvince(userJsonObject.getString("province"));
        userDO.setOpenid(openid);
        userDO.setCountry(userJsonObject.getString("country"));
        userDO.setHeadImgurl(userJsonObject.getString("headimgurl"));
        userDO.setUnionid(userJsonObject.getString("unionid"));
        logger.warn("syn user weixin data {}",userDO);
        //对微信NICK需要进行转义
        userDO.setNickName(BizUtils.filterEmoji(userDO.getNickName()));
        return userDO;
    }

    private String getUserInfoUrl(String accessToken, String openId,boolean isAuth) {
        StringBuilder sb = new StringBuilder();
        if(isAuth){
            sb.append(AUTH_USER_INFO_URL);
        }else{
            sb.append(USER_INFO_URL);
        }
        sb.append("&");
        sb.append("access_token=");
        sb.append(accessToken);
        sb.append("&");
        sb.append("openid=");
        sb.append(openId);
        return sb.toString();
    }
}
