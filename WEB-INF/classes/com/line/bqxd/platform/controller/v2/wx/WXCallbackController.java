package com.line.bqxd.platform.controller.v2.wx;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.ResultEnums;
import com.line.bqxd.platform.common.security.WeixinUtil;
import com.line.bqxd.platform.common.utils.CommonValidatorUtils;
import com.line.bqxd.platform.controller.v2.domain.PfEncryptMsg;
import com.line.bqxd.platform.controller.v2.domain.PfRequestMsg;
import com.line.bqxd.platform.manager.Application;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
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
import com.line.bqxd.platform.v2.security.wxaes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/auth")
public class WXCallbackController {
    private static Logger logger = LoggerFactory.getLogger(WXCallbackController.class);


    @Autowired
    private PfApplication pfApplication;


    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("weixin pf call back into");
        }
        String timestamp = request.getParameter("timestamp");
        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String msgSignature = request.getParameter("msg_signature");
        String encrypType = request.getParameter("encrypt_type");


        if (StringUtils.isNotBlank(msgSignature)) {
            acceptMessage(request, response);
            response.getWriter().write("success");
        } else {
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.write("success");
            } finally {
            }
        }


    }


    private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(PfEncryptMsg.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", PfEncryptMsg.class);
        String xmlMsg = HttpUtil.readBodyByInputStream(in, "UTF-8");
        // 将流转换为字符串
        /*
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        */
        if (logger.isDebugEnabled()) {
            logger.debug("accept message pf {}", xmlMsg);
        }
        // 将xml内容转换为InputMessage对象
        try {
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String msgSignature = request.getParameter("msg_signature");
            PfEncryptMsg pfEncryptMsg = (PfEncryptMsg) xs.fromXML(xmlMsg);
            WXBizMsgCrypt pc = new WXBizMsgCrypt(pfApplication.getToken(), pfApplication.getEncodingAesKey(), pfApplication.getPfAppId());
            String result = null;
            if (StringUtils.isNotBlank(pfEncryptMsg.getAppId())) {
                result = pc.decryptTicketMsg(msgSignature, timestamp, nonce, xmlMsg);
            } else if (StringUtils.isNotBlank(pfEncryptMsg.getToUserName())) {
                result = pc.decryptMsg(msgSignature, timestamp, nonce, xmlMsg);

            }
            if (logger.isDebugEnabled()) {
                logger.debug("accept message pf result = {}", result);
            }
            if (StringUtils.isNotBlank(result)) {
                /*
                xs.processAnnotations(PfRequestMsg.class);
                xs.alias("xml", PfRequestMsg.class);
                result=CommonValidatorUtils.replaceBlank(result);
                */
                Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
                String msg = null;
                PfRequestMsg pfRequestMsg = parse(result);
                if ("component_verify_ticket".equals(pfRequestMsg.getInfoType())) {
                    pfApplication.setComponentVerifyTicket(pfRequestMsg.getComponentVerifyTicket());
                    msg = ("success");
                } else if (StringUtils.isNotBlank(pfRequestMsg.getEvent())) {
                    msg = builderEventString(pfRequestMsg, returnTime);
                } else if (StringUtils.isNotBlank(pfRequestMsg.getContent())) {
                    if ("TESTCOMPONENT_MSG_TYPE_TEXT".equals(pfRequestMsg.getContent())) {
                        msg = builderEventString2(pfRequestMsg, returnTime);
                    } else if (pfRequestMsg.getContent().startsWith("QUERY_AUTH_CODE")) {

                    }
                }

                response.getWriter().write(msg);
            }

            /*
            String rtMsg = messageManager.dispatshMessage(inputMsg, xs);
            if (logger.isDebugEnabled()) {
                logger.debug("message return data = {}", rtMsg);
            }
            if (StringUtils.isNotBlank(rtMsg)) {
                response.getWriter().write("success");
            } else {
                logger.warn("send message is blank");
            }
            */
        } catch (Exception e) {
            logger.error("message handle error", e);
        }
    }

    private String builderEventString(PfRequestMsg pfRequestMsg, Long returnTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[" + pfRequestMsg.getFromUserName() + "]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[" + pfRequestMsg.getToUserName() + "]]></FromUserName>");
        sb.append("<CreateTime>" + returnTime + "</CreateTime>");
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[" + pfRequestMsg.getEvent() + "from_callback]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }

    private String builderEventString2(PfRequestMsg pfRequestMsg, Long returnTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[" + pfRequestMsg.getFromUserName() + "]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[" + pfRequestMsg.getToUserName() + "]]></FromUserName>");
        sb.append("<CreateTime>" + returnTime + "</CreateTime>");
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[TESTCOMPONENT_MSG_TYPE_TEXT_callback]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }

    private PfRequestMsg parse(String xml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(xml);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        PfRequestMsg pfRequestMsg = new PfRequestMsg();
        Element root = document.getDocumentElement();
        NodeList appIdlist = root.getElementsByTagName("AppId");
        NodeList createTimeList = root.getElementsByTagName("CreateTime");
        NodeList infoTypeList = root.getElementsByTagName("InfoType");
        NodeList componentVerifyTicketList = root.getElementsByTagName("ComponentVerifyTicket");

        if (appIdlist != null) {
            pfRequestMsg.setAppId(appIdlist.item(0).getTextContent());
        }
        if (createTimeList != null) {
            pfRequestMsg.setCreateTime(Long.parseLong(createTimeList.item(0).getTextContent()));
        }
        if (infoTypeList != null) {
            pfRequestMsg.setInfoType(infoTypeList.item(0).getTextContent());
        }
        if (componentVerifyTicketList != null) {
            pfRequestMsg.setComponentVerifyTicket(componentVerifyTicketList.item(0).getTextContent());
        }
        try {
            NodeList toUserNameList = root.getElementsByTagName("ToUserName");
            NodeList fromUserNameList = root.getElementsByTagName("FromUserName");
            NodeList msgTypeList = root.getElementsByTagName("MsgType");
            NodeList eventList = root.getElementsByTagName("Event");
            NodeList contentList = root.getElementsByTagName("Content");
            NodeList msgIdList = root.getElementsByTagName("MsgId");
            if (toUserNameList != null) {
                pfRequestMsg.setToUserName(toUserNameList.item(0).getTextContent());
            }
            if (fromUserNameList != null) {
                pfRequestMsg.setFromUserName(fromUserNameList.item(0).getTextContent());
            }
            if (msgTypeList != null) {
                pfRequestMsg.setMsgType(msgTypeList.item(0).getTextContent());
            }
            if (eventList != null) {
                pfRequestMsg.setEvent(eventList.item(0).getTextContent());
            }
            if (contentList != null) {
                pfRequestMsg.setContent(contentList.item(0).getTextContent());
            }
            if (msgIdList != null) {
                pfRequestMsg.setMsgId(msgIdList.item(0).getTextContent());
            }
        } catch (Exception e) {
            logger.error("from xml error", e);
        }

        return pfRequestMsg;
    }


}
