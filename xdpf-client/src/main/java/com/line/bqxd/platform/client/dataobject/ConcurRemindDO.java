package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/5/2.
 * 互助消息体现数据结构
 */
public class ConcurRemindDO extends DBBaseDO implements Serializable {
    @Setter
    @Getter
    private int msgType;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String templateType;
    @Setter
    @Getter
    private String templateId;
    @Setter
    @Getter
    private String url;
    @Setter
    @Getter
    private String data;
    @Setter
    @Getter
    private int status;
    @Setter
    @Getter
    private String dynamic;
    @Setter
    @Getter
    private String bizType;
    @Setter
    @Getter
    private String outBizId;
}
