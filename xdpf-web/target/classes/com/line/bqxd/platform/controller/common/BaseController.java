package com.line.bqxd.platform.controller.common;

import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.common.utils.ParameterTool;
import com.line.bqxd.platform.controller.vo.SignVO;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.domain.ActivityDomain;
import com.line.bqxd.platform.login.SessionConstants;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.Application;

import com.line.bqxd.platform.v2.PfApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/25.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private GlobalObject globalObject;

    @Autowired
    private Application application;

    @Autowired
    private PfApplication pfApplication;

    protected  String  getFullPath(HttpServletRequest request){
        return HttpUtil.getFullPath(request);
    }

    protected String getWSAppId(){
        return application.getAppId();
    }
    protected String getWSAppSecret(){
        return application.getAppSecret();
    }
    protected String getAccessToken(){
        return globalObject.getAccessToken();
    }

    protected String getTicket(){
        return globalObject.getTicket();
    }

    protected String getTicket(String appid) {
        return pfApplication.getJSTicketByAppid(appid);
    }

    protected void setSignView(ModelAndView modelAndView , SignVO vo){
        modelAndView.addObject("wxSign",vo);
    }


    protected void setSignView(ModelAndView modelAndView ,String timestamp,String nonceStr,String signature,String appId){
        SignVO signVO=new SignVO(timestamp,nonceStr,signature,appId);
        signVO.setDebugLevel(globalObject.isDebugLevel());
        setSignView(modelAndView,signVO);
    }
    protected void setSignView(ModelAndView modelAndView ,HttpServletRequest request,String appid){
        String fullPath = this.getFullPath(request);
        Map data = Sign.sign(this.getTicket(appid), fullPath);
        this.setSignView(modelAndView, data.get("timestamp").toString(), data.get("nonceStr").toString(), data.get("signature").toString(),appid);
    }

    protected HttpSession getHttpSession(HttpServletRequest request){
        return request.getSession();
    }

    protected SessionUserInfo getSessionUserInfo(HttpServletRequest request){
        HttpSession session= getHttpSession(request);


        if(session!=null){
            Object object=session.getAttribute(SessionConstants.WX_LOGIN_SESSIOIN_ATTRIBUTE_NAME);
            if(object instanceof  SessionUserInfo){
                return (SessionUserInfo)object;
            }else if(object==null){
                return null;
            }else{
                throw  new ClassCastException("user session cast error,objec="+object.getClass());
            }
        }else{
            return null;
        }


    }

    protected AdminSession getAdminSessionUserInfo(HttpServletRequest request){
        HttpSession session= getHttpSession(request);


        if(session!=null){
            Object object=session.getAttribute(SessionConstants.ADMIN_LOGIN_SESSIOIN_ATTRIBUTE_NAME);
            if(object instanceof  AdminSession){
                return (AdminSession)object;
            }else if(object==null){
                return null;
            }else{
                throw  new ClassCastException("user session cast error,objec="+object.getClass());
            }
        }else{
            return null;
        }


    }

    protected void errorRedirect(HttpServletRequest request, HttpServletResponse response,String errorMsg) {
        StringBuilder sb = new StringBuilder("http://");
        sb.append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(":");
            sb.append(request.getServerPort());
        }
        sb.append("/error/common.htm?errorMsg="+ URLEncoder.encode(errorMsg));

        try {
            String url=ParameterTool.getFullUrl(sb.toString(), request);
            url= url.replaceAll("%25","%");
            response.sendRedirect(url);
        } catch (IOException e) {

        }

    }



}
