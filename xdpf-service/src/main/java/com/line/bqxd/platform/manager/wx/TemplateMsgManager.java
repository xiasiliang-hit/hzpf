package com.line.bqxd.platform.manager.wx;

import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.manager.wx.dataobject.TemplateMsgDO;

/**
 * Created by huangjianfei on 16/7/13.
 */
public interface TemplateMsgManager {

    public void sendWxTemplateNotify(TemplateMsgDO templateMsgDO,String authAppid);

    public TemplateMsgDO getDunkBalanceMsgDO(ConcurPlanDO concurPlanDO,String ensureName,long claimId,String openId, long fee);

    public TemplateMsgDO getJoinSuccessMsgDO(ConcurPlanDO concurPlanDO,String openId, String ensureName, long feee);
}
