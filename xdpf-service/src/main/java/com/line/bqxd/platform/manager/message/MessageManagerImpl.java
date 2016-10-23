package com.line.bqxd.platform.manager.message;

import com.line.bqxd.platform.client.constant.MsgType;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.user.WXUserManager;
import com.line.bqxd.platform.manager.wx.WXManager;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * Created by huangjianfei on 16/4/27.
 */
public class MessageManagerImpl implements MessageManager {

    private static Logger logger = LoggerFactory.getLogger(MessageManagerImpl.class);

    @Resource
    private WXManager wxManager;

    @Autowired
    private WXUserManager wxUserManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private GlobalObject globalObject;

    @Autowired
    private ConcurManager concurManager;

    private static final String DEFAULT_ATTENTION_MSG = buildAttentionMsg();

    private static final String G20_ATTENTION_MSG = builderG20AttentionMsg();



    @Override
    public String dispatshMessage(InputMessage inputMsg, XStream xs) {
        // 取得消息类型
        String msgType = inputMsg.getMsgType();

        if (msgType.equals(MsgType.EVENT.getValue())) {
            return handleEvent(inputMsg, xs);
        } else if (msgType.equals(MsgType.TEXT.getValue())) {
            //return handleText(inputMsg, xs);
            return "";
        }
        return null;
    }

    private String handleEvent(InputMessage inputMsg, XStream xs) {
        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
        String msgType = inputMsg.getMsgType();
        String event = inputMsg.getEvent();

        if (event.equals(Event.SUBSCRIBE.getValue())) {
            try {
                wxManager.wxServeAttention(custermname);
                UserDO userDO = wxUserManager.getSynUserInfo(globalObject.getAccessToken(), custermname, false);
                if (userDO != null) {
                    userManager.updateWXByOpenId(userDO, false);
                    userDO = userManager.selectByOpenId(custermname);
                }
            } catch (Exception e) {
                logger.error("weixin attention error openId=" + custermname, e);
            }
            StringBuffer str = new StringBuffer();
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
            str.append("<CreateTime>" + returnTime + "</CreateTime>");

            //str.append("<MsgType><![CDATA[text]]></MsgType>");
            //str.append("<Content><![CDATA[" + DEFAULT_ATTENTION_MSG + "]]></Content>");

            str.append(G20_ATTENTION_MSG);
            str.append("</xml>");
            return str.toString();
        } else if (event.equals(Event.UNSUBSCRIBE.getValue())) {
            wxManager.wxServeCancleAttention(custermname);
        } else if (event.equals(Event.LOCATION.getValue())) {

        }

        return null;

    }

    private String handleText(InputMessage inputMsg, XStream xs) {

        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
        String msgType = inputMsg.getMsgType();
/*
        StringBuffer str = new StringBuffer();
        str.append("<xml>");
        str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
        str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
        str.append("<CreateTime>" + returnTime + "</CreateTime>");

         str.append("<MsgType><![CDATA[text]]></MsgType>");
         str.append("<Content><![CDATA["+ddd()+"]]></Content>");


        str.append("</xml>");
        return str.toString();
        */
        OutputMessage outputMsg = new OutputMessage();
        outputMsg.setFromUserName(servername);
        outputMsg.setToUserName(custermname);
        outputMsg.setCreateTime(returnTime);
        outputMsg.setMsgType(msgType);
        outputMsg.setContent("你说的是sdfdsf：" + inputMsg.getContent() + "，吗？");
        return xs.toXML(outputMsg);

    }


    public static String buildAttentionMsg() {
        StringBuilder sb = new StringBuilder(100);

        sb.append("欢迎加入扁鹊兄弟互助平台！\n");
        sb.append("\n");
        sb.append("1.这是一个诚信友爱社群\n");
        sb.append("\n");
        sb.append("2.发生重大疾病互助，全体会员分摊互助金\n");
        sb.append("\n");
        sb.append("3.不发生互助则无需花费\n");
        sb.append("\n");
        sb.append("4.公开透明，0元加入，获30万保障\n");
        sb.append("\n");
        sb.append("<a href=\"http://www.xiongdihuzhu.com/concur/detail.htm?channelId=12\"> >>0元加入重大疾病互助计划</a>");
        return sb.toString();
    }

    public static String builderG20AttentionMsg() {

        StringBuilder str = new StringBuilder(100);
        str.append("<MsgType><![CDATA[news]]></MsgType>");
        str.append("<ArticleCount>1</ArticleCount>");
        str.append("<Articles>");
        str.append("<item>");
        str.append("<Title><![CDATA[单双号需要拼车的小伙伴看过来（指引教程）！！]]></Title>");
        str.append("<Description><![CDATA[拼车互助指引教程]]></Description>");
        //str.append("<Description><![CDATA[8月28日至9月6日，杭州每天22小时单双号限行，开车上下班不便怎么办，找人拼车呀！]]></Description>");
        str.append("<PicUrl><![CDATA[http://mmbiz.qpic.cn/mmbiz_png/N7pvYfv3qKzjx2zAoGdmsiaLbHictpXC1yksZZGEn1ibQm432ESEvMSlOn1fjlnPurxpG1tfL3amey7QRj7Fo3ibFA/0?wx_fmt=png]]></PicUrl>");
        //str.append("<Url><![CDATA[https://mp.weixin.qq.com/s?__biz=MzI2MjI3NzEwNA==&mid=100000186&idx=1&sn=1d67786024d4a96bf95c796d7dd142bd&scene=1&srcid=0822FqqwzzqR4CyDeSudcViR&key=b016911c1f1f6dd06bc6bc67ea8c7578619cb830a4ccee2de42159a181a2f09afa60ef7b4aa2da37ab5246c5e76065ea&ascene=0&uin=MTcwODAxNjc0Mw%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=g6s2CasKJdYotIvUq%2FYy9sSv1zxU83zL9iEn2SE5ZFU910jsqL6sjcQr%2BlgD063n]]></Url>");
        str.append("<Url><![CDATA[https://mp.weixin.qq.com/s?__biz=MzI2MjI3NzEwNA==&mid=100000224&idx=1&sn=2a0594332e6e57d71a294ba2a7ecf8d8&scene=1&srcid=08268yMLC83r9K2xJl5Z817K&key=cf237d7ae24775e85cbc9b68b93b14842bef67dc0172fd52e1976a4dc63dde063093ff5896b5688e8ddbdfa8dcfa638b&ascene=0&uin=MTcwODAxNjc0Mw%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=CvBL5U6AiwqJFD%2BfV0DNmy322iU19n2wXbaiKP1iW7DoiQRkl2W1KAALrkgChht2]]</Url>");
        str.append("</item>");
        str.append("</Articles>");
        return str.toString();

    }


}
