package com.line.bqxd.platform.controller.v2.domain;

import com.line.bqxd.platform.client.common.Base;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/9/1.
 */
public class PfEncryptMsg extends Base implements Serializable{

    private static final long serialVersionUID = -1205770854928496233L;
    @XStreamAlias("AppId")
    private String appId;
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("Encrypt")
    private String encrypt;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
