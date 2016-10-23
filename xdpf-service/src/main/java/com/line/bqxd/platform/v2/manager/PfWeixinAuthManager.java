package com.line.bqxd.platform.v2.manager;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.dataobject.GlobalObject;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/6.
 */
public interface PfWeixinAuthManager {

    public void startAuth();

    public boolean insert(PfWeixinAuthDO pfWeixinAuthDO);

    public boolean update(PfWeixinAuthDO pfWeixinAuthDO);

    public boolean updateByAppId(PfWeixinAuthDO pfWeixinAuthDO);

    public List<PfWeixinAuthDO> getAll();

    public PfWeixinAuthDO builderDO(JSONObject queryAuthJson,JSONObject authInfoJson);

    public void addScheduledAuthorizerAccessToken(String appid,boolean isImmediately);

    public PfWeixinAuthDO selectByAppid(String appid);

    public PfWeixinAuthDO selectByCorpid(long corpid);

    public PfWeixinAuthDO selectByid(long id);

    public GlobalObject getGlobalObject(PfWeixinAuthDO pfWeixinAuthDO);


}
