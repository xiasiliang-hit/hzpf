//===================================================================================
// Copyright (c) 2004-2014 by www.taobao.com, All rights reserved.
// 5-6F., Xixi building , 969# wenyixi road, HangZhou, China
// 
// This software is the confidential and proprietary information of 
// Taobao.com, Inc. ("Confidential Information"). You shall not disclose 
// such Confidential Information and shall use it only in accordance 
// with the terms of the license agreement you entered into with Taobao.com, Inc.
//===================================================================================
// File name: HttpResult.java
// Author: longque.zs
// Date: 2014-1-14 上午10:00:47 
//===================================================================================

package com.line.bqxd.platform.common;

import org.apache.commons.httpclient.HttpStatus;

/**
 * LBS_HttpClient返回结果
 *
 * @author longque.zs
 * @version 1.0
 **/

public class HttpResult {
    private int statusCode;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean isSuccess() {
        if (statusCode != HttpStatus.SC_OK) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", content='" + content + '\'' +
                '}';
    }
}
