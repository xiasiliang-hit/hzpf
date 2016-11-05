package com.line.bqxd.platform.manager.sms.impl;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.SmsDO;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.ResultEnums;
import com.line.bqxd.platform.dao.SmsDAO;
import com.line.bqxd.platform.manager.sms.SmsBizDO;
import com.line.bqxd.platform.manager.sms.SmsManager;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class SmsManagerImpl implements SmsManager {
    private static Logger logger = LoggerFactory.getLogger(SmsManagerImpl.class);

    private String method = "alibaba.aliqin.fc.sms.num.send";

    private String appKey;

    private String appSecret;

    private String serverUrl = "http://gw.api.taobao.com/router/rest";

    private String smsType = "normal";

    @Autowired
    private SmsDAO<SmsDO> smsDAO;

    @Override
    public BizResult sendSms(SmsBizDO smsBizDO) {
        SmsDO smsDO = new SmsDO();
        smsDO.setBizType("reg");
        smsDO.setStatus(Status.Sms.NEW.getValue());
        smsDO.setRecNum(smsBizDO.getRecNum());
        smsDO.setChannel("ali");
        smsDO.setCode(smsBizDO.getCode());
        if (!CollectionUtils.isEmpty(smsBizDO.getExtData())) {
            smsDO.setData(JSON.toJSONString(smsBizDO.getExtData()));
        }
        smsDO.setSignName(smsBizDO.getSmsFreeSignName());
        smsDO.setTemplateId(smsBizDO.getSmsTemplateCode());
        try {
            long id = smsDAO.insert(smsDO);
            Map<String, String> map = smsBizDO.getExtData();
            AlibabaAliqinFcSmsNumSendRequest request = new AlibabaAliqinFcSmsNumSendRequest();
            request.setRecNum(smsBizDO.getRecNum());
            request.setSmsTemplateCode(smsBizDO.getSmsTemplateCode());
            request.setSmsType(smsType);
            request.setSmsFreeSignName(smsBizDO.getSmsFreeSignName());
            if (map == null) {
                map = new HashMap<String, String>();
            }
            if (!map.containsKey("code")) {
                map.put("code", smsBizDO.getCode());
            }

            request.setSmsParam(JSON.toJSONString(map));

            DefaultTaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
            AlibabaAliqinFcSmsNumSendResponse response = client.execute(request);
            if (response != null) {
                SmsDO updateSmsDO = new SmsDO();
                updateSmsDO.setId(id);
                if (response.isSuccess()) {
                    updateSmsDO.setStatus(Status.Sms.SUCCESS.getValue());
                } else {
                    updateSmsDO.setStatus(Status.Sms.FAIL.getValue());
                }
                updateSmsDO.setErrCode(response.getErrorCode());
                updateSmsDO.setMsg(response.getMsg());
                boolean rt = smsDAO.update(updateSmsDO);
                if (rt && response.isSuccess()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("sms send to channel success {},result={}", smsBizDO, rt);
                    }
                    return new BizResult(ResultEnum.SUCCESS);
                } else {
                    if (!response.isSuccess()) {
                        String subCode = response.getSubCode();
                        String subMsg = response.getSubMsg();

                        logger.warn("sms send to channel fail, {},errorCode={},msg={}", smsBizDO, response.getErrorCode() + "_" + subCode, response.getMsg() + "_" + subMsg);
                        if("isv.BUSINESS_LIMIT_CONTROL".equals(subCode)){
                            return new BizResult(ResultEnum.BIZ_MOBILE_SEND_MINUTE_ONE_FAIL);
                        }
                    } else {
                        logger.warn("sms send to channel success {},result={}", smsBizDO, rt);
                    }
                }
            } else {
                logger.warn("sms send to channel fail,result=null");
            }
        } catch (Exception e) {
            logger.error("send sms fail " + smsBizDO, e);
        }
        return new BizResult(ResultEnum.MOBILE_VERIFY_SEND_FAIL);
    }

    @Override
    public boolean verifySms(String code, String recNum, boolean updateStatus) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(recNum)) {
            logger.warn("verify sms argument  fail code={},recNum={}", code, recNum);
            return false;
        }
        SmsDO smsDO = smsDAO.getSmsByRecNumAndStatus(recNum, 2);
        if (smsDO != null) {
            if (code.equals(smsDO.getCode())) {
                if (updateStatus) {
                    smsDO.setStatus(Status.Sms.USED.getValue());
                    smsDAO.update(smsDO);
                }
                if(logger.isDebugEnabled()){
                    logger.debug("verify sms success,result=null code={},recNum={}", code, recNum);
                }
                return true;
            }
        } else {
            logger.warn("verify sms fail,result=null code={},recNum={}", code, recNum);
        }

        return false;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
