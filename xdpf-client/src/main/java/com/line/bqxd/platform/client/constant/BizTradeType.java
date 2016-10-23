package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/12.
 */
public enum BizTradeType {
    PAY("pay"),CASH("cash"),FILL("fill"),BACK("back");
    private String value;

    BizTradeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

