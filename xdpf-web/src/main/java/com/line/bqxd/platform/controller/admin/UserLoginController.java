package com.line.bqxd.platform.controller.admin;

import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.client.dataobject.UserAdminDO;
import com.line.bqxd.platform.common.utils.UrlTool;
import com.line.bqxd.platform.dao.UserAdminDAO;
import com.line.bqxd.platform.login.SessionConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/adminLogin")
public class UserLoginController {
    private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    //private final static String DEFAULT_LOGIN_SUCCESS_URL="http://www.xiongdihuzhu.com/admin/index.htm";

    private final static String DEFAULT_LOGIN_SUCCESS_CONTEXT="/admin/index.htm";
    @Autowired
    private UserAdminDAO<UserAdminDO> userAdminDAO;
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        String redirectUri=request.getParameter("redirect_uri");
        mav.addObject("redirectUri",redirectUri);
        mav.setViewName("admin/login");

        return mav;
    }

    @RequestMapping("/loginAction")
    public String loginAction(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, HttpServletRequest request, HttpServletResponse reponse, Model model) {
        String redirectUri=request.getParameter("redirect_uri");
        UserAdminDO userAdminDO=userAdminDAO.getUserAdminByLoginName(userName);
        if(userAdminDO!=null&&userAdminDO.getPassword().equals(password)){
            HttpSession session=request.getSession();
            AdminSession adminSession =new AdminSession(userName);
            try {
                session.setAttribute(SessionConstants.ADMIN_LOGIN_SESSIOIN_ATTRIBUTE_NAME,adminSession);
                if (StringUtils.isNotBlank(redirectUri)) {
                    reponse.sendRedirect(redirectUri);
                } else {
                    String url= UrlTool.getAbsoluteUrl(request, DEFAULT_LOGIN_SUCCESS_CONTEXT);
                    reponse.sendRedirect(url);
                }

            }catch (Exception e){
                logger.error("loginAction error",e);
            }
        }else{
            return "admin/login";
        }
        return "admin/login";
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/index");
        return mav;
    }

}
