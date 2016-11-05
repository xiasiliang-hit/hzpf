package com.line.bqxd.platform.controller.admin;

import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.client.dataobject.UserAdminDO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final static String DEFAULT_LOGIN_SUCCESS_URL="http://www.xiongdihuzhu.com/admin/index.htm";
    @Autowired
    private UserAdminDAO<UserAdminDO> userAdminDAO;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/index");
        return mav;
    }

}
