package com.line.bqxd.platform.common;

/**
 * Created by huangjianfei on 16/4/29.
 */
public enum ResultEnums {
    SUCCESS("SUCCESS","OK"),
    SYSERROR("SYSERROR","系统错误"),
    /*微信下单支付异常*/
    WXPAY_TRADE_ORDERPAID("WXPAY_TRADE_ORDERPAID","商户订单已支付"),

    /*支付通知处理异常*/
    WXPAY_NOTIFY_SIGN_FIALE("WXPAY_NOTIFY_SIGN_FIALE","签名异常");
    ;
    private String code;

    private String desc;

    ResultEnums(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
