package com.line.bqxd.platform.manager;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjianfei on 16/7/11.
 */
public class PayTestMapping {
    private Map<String, String> openIdMap = new HashMap<String, String>();

    private Map<String, String> openIdMapReverse = new HashMap<String, String>();


    private String payTestAppId = null;

    public void setOpenIdMap(Map<String, String> openIdMap) {
        this.openIdMap = openIdMap;
    }


    public String getPayTestAppId() {
        return payTestAppId;
    }

    public void setPayTestAppId(String payTestAppId) {
        this.payTestAppId = payTestAppId;
    }

    public String getMappingOpenIdFromDev(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return openIdMap.get(openId);
    }


    public void setOpenIdMapReverse(Map<String, String> openIdMapReverse) {
        this.openIdMapReverse = openIdMapReverse;
    }

    public String getMappingOpenIdFromOnline(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return openIdMapReverse.get(openId);
    }
}
