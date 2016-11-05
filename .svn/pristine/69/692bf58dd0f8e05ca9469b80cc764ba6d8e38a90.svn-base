package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.dataobject.SmsDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
public interface SmsDAO <T> extends BaseDAO {



    /**
     * 获取最新验证码,根据手机号码和状态
     * @param recNum
     * @param status
     * @return
     */
    public SmsDO getSmsByRecNumAndStatus(String recNum,int status);

    /**
     * 根据手机号码获取短信验证码列表
     * @param recNum
     * @return
     */
    public List<SmsDO> getSmsByRecNum(String recNum);
}
