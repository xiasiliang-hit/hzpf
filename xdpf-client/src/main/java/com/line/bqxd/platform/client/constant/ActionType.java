package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/15.
 */
public enum ActionType {
    JOIN("join"),ENTER("enter");

    private String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

