package com.line.bqxd.platform.manager.sms;

import com.line.bqxd.platform.common.BizResult;

/**
 * Created by huangjianfei on 16/5/6.
 */
public interface SmsManager {

    /**
     * 发送短息
     * @param smsDO
     * @return
     */
    public BizResult sendSms(SmsBizDO smsDO);

    /**
     * 短信验证码校验
     * @param code 校验码
     * @param recNum 手机号码
     * @param updateStatus 是否更新状态
     * @return
     */
    public boolean verifySms(String code,String recNum,boolean updateStatus);
}
