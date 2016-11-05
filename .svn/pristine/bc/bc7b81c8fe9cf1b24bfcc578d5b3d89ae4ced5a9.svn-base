package com.line.bqxd.platform.common;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luohai.hjf on 14-3-19.
 */
public class HttpRetryHandle extends DefaultHttpMethodRetryHandler {

    private static Logger log = LoggerFactory.getLogger(HttpRetryHandle.class);
    
    public boolean retryMethod(
            final HttpMethod method,
            final IOException exception,
            int executionCount) {

        boolean result=super.retryMethod(method,exception,executionCount);
        if(!result){
            if (exception instanceof SocketTimeoutException) {
                log.error("socket time out url="+method.getQueryString());
                return true;
            }
        }

        return result;
    }
}
