package com.line.bqxd.platform.controller.v2.manager.pfadmin;

import com.line.bqxd.platform.client.dataobject.UserAdminDO;
import com.line.bqxd.platform.dao.UserAdminDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/v2/manager")
public class PfAdminController {
    private static Logger logger = LoggerFactory.getLogger(PfAdminController.class);

    @Autowired
    private UserAdminDAO<UserAdminDO> userAdminDAO;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/v2/manager/index");
        return mav;
    }

}
