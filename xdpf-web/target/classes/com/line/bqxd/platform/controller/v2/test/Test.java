package com.line.bqxd.platform.controller.v2.test;

import com.line.bqxd.platform.controller.v2.domain.PfEncryptMsg;
import com.line.bqxd.platform.controller.v2.domain.PfRequestMsg;
import com.line.bqxd.platform.manager.message.OutputMessage;
import com.line.bqxd.platform.manager.message.SerializeXmlUtil;
import com.thoughtworks.xstream.XStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by huangjianfei on 16/6/1.
 */
public class Test {
    public static void main(String[] agrs) throws Exception{
        String str="<xml><AppId><![CDATA[wxbca333b225839e32]]></AppId>\n" +
                "<CreateTime>1472804567</CreateTime>\n" +
                "<InfoType><![CDATA[component_verify_ticket]]></InfoType>\n" +
                "<ComponentVerifyTicket><![CDATA[ticket@@@iYF_WqaDqI3rV35jqfV330paAbUkgo2JKqN8P3EUIz4l1LM3MfPeRZixey3pv9ukyN8C8EakbfQuIyy1SVJ5aA]]></ComponentVerifyTicket>\n" +
                "</xml>\n";
        String xmlMsgg="<xml><AppId><![CDATA[wxbca333b225839e32]]></AppId><CreateTime>1473128566</CreateTime><InfoType><![CDATA[component_verify_ticket]]></InfoType><ComponentVerifyTicket><![CDATA[ticket@@@N0r8OyUcWnplv8c5QnqywXxgu5xE4pcX6oANqpCkbrCB2CM-wrm9STPEltEzHEukd1cLuW0ALWuvJvnK8k2pYQ]]></ComponentVerifyTicket></xml>";

        XStream xs = SerializeXmlUtil.createXstream();
        //xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.processAnnotations(PfEncryptMsg.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", PfEncryptMsg.class);

        xs.processAnnotations(PfRequestMsg.class);
        xs.alias("xml", PfRequestMsg.class);
        PfRequestMsg pfRequestMsg = parse(xmlMsgg);
        System.out.println(pfRequestMsg);
    }

    private static PfRequestMsg parse(String xml) throws Exception{
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
        if(appIdlist!=null){
            pfRequestMsg.setAppId(appIdlist.item(0).getTextContent());
        }
        if(createTimeList!=null){
            pfRequestMsg.setCreateTime(Long.parseLong(createTimeList.item(0).getTextContent()));
        }
        if(infoTypeList!=null){
            pfRequestMsg.setInfoType(infoTypeList.item(0).getTextContent());
        }
        if(componentVerifyTicketList!=null){
            pfRequestMsg.setComponentVerifyTicket(componentVerifyTicketList.item(0).getTextContent());
        }

        return pfRequestMsg;
    }

}
