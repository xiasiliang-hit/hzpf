package com.line.bqxd.platform.common.exception;

/**
 * Created by huangjianfei on 16/5/18.
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }



    public BusinessException(String message) {
        super(message);
    }



    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }



    public BusinessException(Throwable cause) {
        super(cause);
    }



    protected BusinessException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
