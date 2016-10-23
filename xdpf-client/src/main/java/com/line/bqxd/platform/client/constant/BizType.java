package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/3.
 */
public enum BizType {
    CONCUR("concur"),AMUSE("amuse");

    private String value;

    BizType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
