package com.line.bqxd.platform.client.constant;

import org.apache.commons.lang.StringUtils;

/**
 * Created by huangjianfei on 16/5/3.
 */
public enum BizFeeType {
    MEMBERFEE("memberfee"),CONCURFEE("concurfee");

    private String value;

    BizFeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BizFeeType of(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        if(value.equals(MEMBERFEE.getValue())){
            return MEMBERFEE;
        }
        if(value.equals(CONCURFEE.getValue())){
            return CONCURFEE;
        }

        return null;
    }
}
