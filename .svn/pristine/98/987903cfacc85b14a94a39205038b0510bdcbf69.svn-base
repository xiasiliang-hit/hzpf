package com.line.bqxd.platform.controller.v2.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.PfWeixinAuthDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.ResultEnums;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.message.InputMessage;
import com.line.bqxd.platform.manager.message.MessageManager;
import com.line.bqxd.platform.manager.message.OutputMessage;
import com.line.bqxd.platform.manager.message.SerializeXmlUtil;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wx.TemplateMsgManager;
import com.line.bqxd.platform.manager.wx.dataobject.TemplateMsgDO;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXBaseDO;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayNotifyRequest;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/msg")
public class PfMsgController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PfMsgController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private UserPayManager userPayManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private TemplateMsgManager templateMsgManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @RequestMapping(value = "/{appid}/callback")
    @ResponseBody
    public void callback(@PathVariable String appid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        logger.warn("wx callback msg appid={}", appid);
        acceptMessage(request, response);
    }

    private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(InputMessage.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", InputMessage.class);
        String xmlMsg = HttpUtil.readBodyByInputStream(in, "UTF-8");

        // 将流转换为字符串
        if (logger.isDebugEnabled()) {
            logger.debug("accept message {}", xmlMsg);
        }
        if (StringUtils.isBlank(xmlMsg)) {
            return;
        }
        // 将xml内容转换为InputMessage对象
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg);
        try {
            String rtMsg = messageManager.dispatshMessage(inputMsg, xs);
            if (logger.isDebugEnabled()) {
                logger.debug("message return data = {}", rtMsg);
            }
            if (StringUtils.isNotBlank(rtMsg)) {
                response.getWriter().write(rtMsg);
            } else {
                logger.warn("send message is blank");
            }
        } catch (Exception e) {
            logger.error("message handle error", e);
        }
    }


    @RequestMapping(value = "/{appid}/payNotifyMessage", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void payNotifyMessage(@PathVariable String appid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        String result = null;
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(WXPayNotifyRequest.class);
        xs.processAnnotations(WXBaseDO.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", WXPayNotifyRequest.class);
        try {
            // 将流转换为字符串
            StringBuilder xmlMsg = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                xmlMsg.append(new String(b, 0, n, "UTF-8"));
            }
            // 将xml内容转换为WXPayNotifyResult对象
            WXPayNotifyRequest wxPayNotifyRequest = (WXPayNotifyRequest) xs.fromXML(xmlMsg.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("wxPayNotifyRequest get data {}", wxPayNotifyRequest);
            }
            xs.alias("xml", WXBaseDO.class);

            if (wxPayNotifyRequest == null) {
                logger.warn("get wxPayNotifyRequest null");
                response.getWriter().write(xs.toXML(new WXBaseDO(WXBaseDO.WX_FAIL_CODE, "fail")));
            }

            if (WXBaseDO.WX_SUCCESS_CODE.equals(wxPayNotifyRequest.getReturnCode())) {
                if (WXBaseDO.WX_SUCCESS_CODE.equals(wxPayNotifyRequest.getResultCode())) {
                    //TODO 需要验证签名
                    String transactionId = wxPayNotifyRequest.getTransactionId();
                    if (userPayManager.userTradeFillCallback(wxPayNotifyRequest)) {
                        logger.warn("payNotifyMessage execute userTradeFillCallback success,transactionId={},{}", transactionId, wxPayNotifyRequest);
                    } else {
                        logger.warn("payNotifyMessage execute userTradeFillCallback fail,transactionId={},{}", transactionId, wxPayNotifyRequest);
                    }
                    //需要给用户发消息
                    result = xs.toXML(new WXBaseDO(ResultEnums.SUCCESS));
                    String openId = wxPayNotifyRequest.getOpenId();
                    ;

                    String bizFeeType = getBizFeeType(wxPayNotifyRequest);
                    logger.info("payNotifyMessage callback bizFeeType={}", bizFeeType);
                    if (BizFeeType.MEMBERFEE.getValue().equals(bizFeeType)) {
                        userManager.updateMemberFee(openId, Long.parseLong(wxPayNotifyRequest.getTotalFee()));
                    } else if (BizFeeType.CONCURFEE.getValue().equals(bizFeeType)) {
                        try {
                            Map<String, String> map = getAttachMap(wxPayNotifyRequest);

                            if (map.containsKey("concurId")) {
                                ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(Long.parseLong(map.get("concurId")));
                                String ensureName = ensureName(map.get("ids"));
                                TemplateMsgDO templateMsgDO = templateMsgManager.getJoinSuccessMsgDO(concurPlanDO, wxPayNotifyRequest.getOpenId(), ensureName, Long.parseLong(wxPayNotifyRequest.getTotalFee()));
                                PfWeixinAuthDO pfWeixinAuthDO = pfWeixinAuthManager.selectByid(concurPlanDO.getPfAppId());
                                templateMsgManager.sendWxTemplateNotify(templateMsgDO, pfWeixinAuthDO.getAuthorizerAppid());
                            }
                        } catch (Exception e) {
                            logger.error("send msg fail", e);
                        }
                    }
                } else {
                    result = xs.toXML(new WXBaseDO(ResultEnums.SYSERROR));
                }
            } else {
                result = xs.toXML(new WXBaseDO(ResultEnums.SYSERROR));
            }
        } catch (Exception e) {
            logger.error("payNotifyMessage execute error", e);
            result = xs.toXML(new WXBaseDO(ResultEnums.SYSERROR));
        }
        //坑,为什么下杠转变出来变成双下杠
        result = result.replaceAll("__", "_");
        if (StringUtils.isNotBlank(result)) {
            try {
                logger.warn("payNotifyMessage execute {}", result);
                response.getWriter().write(result);
            } catch (Exception e) {
                logger.error("payNotifyMessage response write error", e);
            }
        } else {
            logger.warn("payNotifyMessage execute result null");
        }

    }

    private String getBizFeeType(WXPayNotifyRequest wxPayNotifyRequest) {
        if (wxPayNotifyRequest == null) {
            return null;
        }
        String attach = wxPayNotifyRequest.getAttach();
        if (StringUtils.isBlank(attach)) {
            return null;
        }
        Map<String, String> map = (Map) JSON.parse(attach);
        if (map != null) {
            return map.get("bizFeeType");
        } else {
            return null;
        }

    }

    private Map<String, String> getAttachMap(WXPayNotifyRequest wxPayNotifyRequest) {

        String attach = wxPayNotifyRequest.getAttach();
        if (StringUtils.isBlank(attach)) {
            return null;
        }
        Map<String, String> map = (Map) JSON.parse(attach);
        return map;

    }

    private String ensureName(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(";");
            if (idArray != null && idArray.length > 0) {
                List<Long> idList = new ArrayList<Long>();
                for (String id : idArray) {
                    idList.add(Long.parseLong(id));
                }
                List<UserConcurRelationDO> userConcurRelationDOList = concurManager.selectUserConcurRelationByIdS(idList);
                StringBuilder sb = new StringBuilder();
                if (!CollectionUtils.isEmpty(userConcurRelationDOList)) {
                    for (UserConcurRelationDO userConcurRelationDO : userConcurRelationDOList) {
                        sb.append(userConcurRelationDO.getEnsureName());
                    }
                }
                return sb.toString();
            }
        }
        return "";
    }

}
