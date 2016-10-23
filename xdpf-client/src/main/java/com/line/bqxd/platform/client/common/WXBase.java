package com.line.bqxd.platform.client.common;

import com.alibaba.fastjson.JSON;

/**
 * Created by huangjianfei on 16/5/4.
 */
public class WXBase extends Base{
    private String errmsg;
    private int errcode;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

}
