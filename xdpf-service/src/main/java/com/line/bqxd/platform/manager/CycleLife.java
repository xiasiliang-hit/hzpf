package com.line.bqxd.platform.manager;

/**
 * Created by huangjianfei on 16/5/21.
 * 简单生命周期接口
 */
public interface CycleLife {

    public void init();
    public void reInit();

    public void stop();

}
