package com.line.bqxd.platform.manager.message;

/**
 * Created by huangjianfei on 16/4/27.
 */
public enum Event {
    SUBSCRIBE("subscribe"),UNSUBSCRIBE("unsubscribe"),LOCATION("location"),CLICK("click");
    private String value=null;
    Event(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
