package com.line.bqxd.platform.controller.common;

import com.line.bqxd.platform.manager.RunEnv;

/**
 * Created by huangjianfei on 16/9/29.
 */
public class VerifyException extends RuntimeException {
    private int index;
    public VerifyException(int index) {
        super();
        this.index=index;
    }

    public VerifyException(String message,int index) {
        super(message);
        this.index=index;
    }

    public VerifyException(String message, Throwable cause,int index) {
        super(message, cause);
        this.index=index;

    }

    public VerifyException(Throwable cause,int index) {
        super(cause);
        this.index=index;
    }

    protected VerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,int index) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.index=index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
