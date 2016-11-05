package com.line.bqxd.platform.manager;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by huangjianfei on 16/7/11.
 */
public class RunEnv {
    public static final String ONLINE_DOMAIN="http://www.xiongdihuzhu.com";
    public static final String DEV_DOMAIN="http://www.xiantiaokeji.com";
    public static final String ONLINE_RES_DOMAIN="http://res.xiongdihuzhu.com";
    public static final String DEV_RES_DOMAIN="http://res.xiantiaokeji.com";
    private String env=null;

    private boolean isDev=true;

    public synchronized void init() {
        if (!StringUtils.isBlank(env)) {
            if (env.equalsIgnoreCase("online")) {
                isDev = false;
            } else {
                isDev = true;
            }
        } else {
            isDev = false;
        }
    }

    public void setEnv(String env) {
        this.env = env;
    }


    public boolean isDev() {
        return isDev;
    }

    public boolean isOnline() {
        return isDev == false ? true : false;
    }


}
