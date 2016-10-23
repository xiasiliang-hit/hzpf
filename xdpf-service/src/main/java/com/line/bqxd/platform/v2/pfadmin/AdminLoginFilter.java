package com.line.bqxd.platform.v2.pfadmin;

import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.common.HttpUtil;
import com.line.bqxd.platform.common.utils.UrlTool;
import com.line.bqxd.platform.login.SessionConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by huangjianfei on 16/4/26.
 */
public class AdminLoginFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AdminLoginFilter.class);

    private static final String BQXD_ADMIN_LOGIN_URL = "/v2/managerLogin/login.htm?";


    private String notPermitUrls[] = null;
    private String loginUrl = null;
    private ServletContext servletContext = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String notPermitUrls = filterConfig.getInitParameter("notPermitUrls");
        loginUrl = filterConfig.getInitParameter("gotoUrl");


        if (StringUtils.isNotBlank(notPermitUrls)) {
            this.notPermitUrls = notPermitUrls.split(",");
        }
        servletContext = filterConfig.getServletContext();

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (!isPermit(req)) {
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext, "org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring");
           // Application application = (Application) getBean("application", ctx);
           // UserManager userManager = (UserManager) getBean("userManager", ctx);
            setMenuActive(req);
            HttpSession session = req.getSession(true);
            Object wxSession = session.getAttribute(SessionConstants.ADMIN_LOGIN_SESSIOIN_ATTRIBUTE_NAME);
            resp.addHeader("Access-Control-Allow-Origin ","*");
            //用户已经进行授权或者登陆
            if (wxSession != null && StringUtils.isNotBlank(((AdminSession) wxSession).getUserName())) {
                if (logger.isDebugEnabled()) {
                    logger.debug(wxSession + " admin user into system");
                }
                chain.doFilter(request, response);
                return;
            } else {
                //用户未授权,需要进行微信登录授权
                resp.sendRedirect(getAuthUserInfoUrl(req));
                return;
            }
        } else {
            chain.doFilter(request, response);
            return;
        }


    }


    @Override
    public void destroy() {

    }

    private boolean isPermit(HttpServletRequest req) {
        String path = req.getContextPath();
        String servletPath = req.getServletPath();
        if (notPermitUrls == null || notPermitUrls.length == 0) {
            return true;
        }
        for (String notPermitUrl : notPermitUrls) {
            if (servletPath.startsWith(notPermitUrl)) {
                return false;
            }
        }
        return true;
    }

    private Object getBean(String beanName, ApplicationContext ctx) {
        return ctx.getBean(beanName);
    }

    private String getAuthUserInfoUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(UrlTool.getAbsoluteUrl(request, BQXD_ADMIN_LOGIN_URL));
        sb.append("redirect_uri=");
        try {
            sb.append(URLEncoder.encode(HttpUtil.getFullPath(request), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        return sb.toString();
    }


    private void setMenuActive(HttpServletRequest req) {
        String servletPath = req.getServletPath();
        if (StringUtils.isBlank(servletPath)) {
            return;
        }
        //StringUtils.split()
        String paths[] = StringUtils.split(servletPath,"/");
        if (paths.length >= 3) {
            req.setAttribute("menu_active", paths[2]);
        } else if (paths.length >= 2) {
            req.setAttribute("menu_active", paths[1]);
        }
    }

}
