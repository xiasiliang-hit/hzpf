package com.line.bqxd.platform.manager.message;

import com.thoughtworks.xstream.XStream;

/**
 * Created by huangjianfei on 16/4/27.
 */
public interface MessageManager {
    public String dispatshMessage(InputMessage inputMsg,XStream xs);
}
