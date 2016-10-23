package com.line.bqxd.platform.manager.user;

import com.line.bqxd.platform.client.dataobject.UserDO;

/**
 * Created by huangjianfei on 16/5/16.
 */
public interface WXUserManager {

    UserDO getSynUserInfo(String accessToken, String openid,boolean isAuth)throws Exception;

}
