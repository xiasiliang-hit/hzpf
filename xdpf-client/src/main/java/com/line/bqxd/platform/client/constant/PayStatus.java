package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/4/28.
 */
public enum PayStatus {
    UNPAY(1)/*待付款*/,PAYSUCCESS(2)/*付款成功*/,PAYFAIL(3)/*付款失败*/,REFUND(4)/*退款*/;

    private int value=0;

    PayStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
