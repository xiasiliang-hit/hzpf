package com.line.bqxd.platform.manager.wx.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.HttpResult;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wx.TemplateMsgManager;
import com.line.bqxd.platform.manager.wx.dataobject.TemplateMsgDO;
import com.line.bqxd.platform.v2.PfApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by huangjianfei on 16/7/13.
 */
public class TemplateMsgManagerImpl implements TemplateMsgManager {

    private static Logger logger = LoggerFactory.getLogger(TemplateMsgManagerImpl.class);

    private String dunkBalanceMsgId = "";

    private String joinSuccessMsgId = "";

    private static final String SEND_NOTIFY_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    private static final String MSG_NOTIFY_URL = "http://pf.xiongdihuzhu.com/v2/concur/my.htm";

    @Autowired
    private UserManager userManager;

    @Autowired
    private PfApplication pfApplication;


    @Override
    public void sendWxTemplateNotify(TemplateMsgDO templateMsgDO,String authAppid) {
        if (templateMsgDO == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("sendWxTemplateNotify track, {}", templateMsgDO);
        }

        String url = SEND_NOTIFY_URL + pfApplication.getAccessTokenByAppid(authAppid);;
        HttpResult result = null;
        try {
            result = HttpUtil.sendOriginally(url, JSON.toJSONString(templateMsgDO), "UTF-8");
            if (result == null || !result.isSuccess()) {
                logger.warn("sendWxTemplateNotify result fail,{}, {}", templateMsgDO, result == null ? null : result);
            } else {

                JSONObject jsonObject = JSONObject.parseObject(result.getContent());
                if (jsonObject.getString("errcode").equals("0") && jsonObject.getString("errmsg").equals("ok")) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("sendWxTemplateNotify request success {}", result);
                    }
                } else {
                    logger.warn("sendWxTemplateNotify send fail,{},{}", templateMsgDO, result);

                }
            }
        } catch (Exception e) {
            logger.error("sendWxTemplateNotify send error," + templateMsgDO + "," + result, e);
        }
    }

    @Override
    public TemplateMsgDO getDunkBalanceMsgDO(ConcurPlanDO concurPlanDO,String ensureName, long claimId, String openId, long fee) {
        TemplateMsgDO templateMsgDO = null;
        try {
            templateMsgDO = new TemplateMsgDO(openId, dunkBalanceMsgId, buildUrl(concurPlanDO,claimId));
            templateMsgDO.setFirst(new TemplateMsgDO.ValueColor("平台受助人"+ensureName+"的互助事件扣费提醒", "#173177"));
            templateMsgDO.setRemark(new TemplateMsgDO.ValueColor("点击此处立即查看公示详情。", "#173177"));
            templateMsgDO.put("keyword1", new TemplateMsgDO.ValueColor(BizUtils.changeF2Y(fee), "#173177"));
            templateMsgDO.put("keyword2", new TemplateMsgDO.ValueColor(DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT3, new Date()), "#173177"));

        } catch (Exception e) {
            logger.error("getFillCashMsgDO error", e);
        }

        return templateMsgDO;
    }

    @Override
    public TemplateMsgDO getJoinSuccessMsgDO(ConcurPlanDO concurPlanDO,  String openId, String ensureName, long fee) {
        TemplateMsgDO templateMsgDO = null;
        try {
            templateMsgDO = new TemplateMsgDO(openId, joinSuccessMsgId, buildUrl(concurPlanDO));
            templateMsgDO.setFirst(new TemplateMsgDO.ValueColor(ensureName+"已成功加入互助计划", "#173177"));
            templateMsgDO.setRemark(new TemplateMsgDO.ValueColor("点击进入\"我的互助\",查看详情。", "#173177"));
            //templateMsgDO.put("accountName", new TemplateMsgDO.ValueColor(userName, "#173177"));
            templateMsgDO.put("keyword2", new TemplateMsgDO.ValueColor(DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT3, new Date()), "#173177"));
            templateMsgDO.put("keyword1", new TemplateMsgDO.ValueColor(BizUtils.changeF2Y(fee) + "元", "#173177"));
            //templateMsgDO.put("result", new TemplateMsgDO.ValueColor("充值成功", "#173177"));

        } catch (Exception e) {
            logger.error("getFillCashMsgDO error", e);
        }

        return templateMsgDO;
    }

    public void setDunkBalanceMsgId(String dunkBalanceMsgId) {
        this.dunkBalanceMsgId = dunkBalanceMsgId;
    }

    public void setJoinSuccessMsgId(String joinSuccessMsgId) {
        this.joinSuccessMsgId = joinSuccessMsgId;
    }

    public String buildUrl(ConcurPlanDO concurPlanDO) {
        //?pfAppid=4&concurPlanId=4
        StringBuilder sb = new StringBuilder(MSG_NOTIFY_URL);
        sb.append('?');
        sb.append("pfAppid=");
        sb.append(concurPlanDO.getPfAppId());
        sb.append("&concurPlanId=");
        sb.append(concurPlanDO.getId());
        return sb.toString();
    }

    public String buildUrl(ConcurPlanDO concurPlanDO, long claimId) {
        //?pfAppid=4&concurPlanId=4
        StringBuilder sb = new StringBuilder(MSG_NOTIFY_URL);
        sb.append('?');
        sb.append("pfAppid=");
        sb.append(concurPlanDO.getPfAppId());
        sb.append("&concurPlanId=");
        sb.append(concurPlanDO.getId());
        sb.append("&id=");
        sb.append(claimId);
        return sb.toString();
    }
}
