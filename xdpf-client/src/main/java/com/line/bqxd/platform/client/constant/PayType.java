package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/3.
 */
public enum PayType {
    ALIPAY("alipay"),WXPAY("wxpay"),BANK("bank");
    private String value;

    PayType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
