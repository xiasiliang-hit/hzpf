package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/9.
 */
public enum SmsStatus {
    NEW(1),SUCCESS(2),FAIL(3),USED(4);
    private int value=0;

    SmsStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
