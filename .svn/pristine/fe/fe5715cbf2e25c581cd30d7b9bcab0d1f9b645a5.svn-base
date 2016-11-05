package com.line.bqxd.platform.controller.safety;

import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.common.security.WeixinUtil;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.Application;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/safety")
public class WxJsTestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WxJsTestController.class);

    @Autowired
    private GlobalObject globalObject;
    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        //String js_accessToken = globalObject.getAccessToken();  //获取微信jssdk---access_token
        //String jsapi_ticket = globalObject.getTicket(); //获取微信jssdk---ticket
        //System.out.println("jsapi_ticket==="+jsapi_ticket);
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        //获取完整的URL地址
        String fullPath= this.getFullPath(request);
        Map data = Sign.sign(this.getTicket(), fullPath);

        ModelAndView mav = new ModelAndView();
        //this.setSignView(mav,data.get("timestamp").toString(),data.get("nonceStr").toString(),data.get("signature").toString());
        /*
        mav.addObject("timestamp", data.get("timestamp"));
        mav.addObject("nonceStr", data.get("nonceStr"));
        mav.addObject("signature", data.get("signature"));
        */
        mav.setViewName("safety/test");
        return mav;
    }

    @RequestMapping("/share")
    public ModelAndView share(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        //String js_accessToken = globalObject.getAccessToken();  //获取微信jssdk---access_token
        //String jsapi_ticket = globalObject.getTicket(); //获取微信jssdk---ticket
        //System.out.println("jsapi_ticket==="+jsapi_ticket);

        //获取完整的URL地址
        String fullPath= this.getFullPath(request);
        Map data = Sign.sign(this.getTicket(), fullPath);

        ModelAndView mav = new ModelAndView();
        //this.setSignView(mav,data.get("timestamp").toString(),data.get("nonceStr").toString(),data.get("signature").toString());
        /*
        mav.addObject("timestamp", data.get("timestamp"));
        mav.addObject("nonceStr", data.get("nonceStr"));
        mav.addObject("signature", data.get("signature"));
        */
        mav.setViewName("safety/share");
        return mav;
    }

}
