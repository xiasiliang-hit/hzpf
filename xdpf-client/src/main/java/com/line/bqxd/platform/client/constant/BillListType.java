package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/3.
 */
public enum BillListType {
    FILL("fill"),PAY("pay"),CASH("cash");

    private String value;

    BillListType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
