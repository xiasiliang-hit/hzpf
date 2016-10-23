package com.line.bqxd.platform.controller.v2.domain;

import com.line.bqxd.platform.client.common.Base;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.w3c.dom.NodeList;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/9/1.
 */
public class PfRequestMsg  extends Base implements Serializable{


    private static final long serialVersionUID = 337203636825596480L;

    @XStreamAlias("AppId")
    private String appId;

    @XStreamAlias("CreateTime")
    private long createTime;

    @XStreamAlias("InfoType")
    private String infoType;

    @XStreamAlias("ComponentVerifyTicket")
    private String componentVerifyTicket;

    @XStreamAlias("toUserName")
    private String toUserName=null;

    @XStreamAlias("fromUserName")
    private String fromUserName=null;

    @XStreamAlias("msgType")
    private String msgType=null;

    @XStreamAlias("event")
    private String event=null;

    @XStreamAlias("content")
    private String content=null;

    @XStreamAlias("msgId")
    private String msgId=null;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getComponentVerifyTicket() {
        return componentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        this.componentVerifyTicket = componentVerifyTicket;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
