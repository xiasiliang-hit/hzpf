package com.line.bqxd.platform.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/test")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    private final static String DEFAULT_LOGIN_SUCCESS_URL="http://www.xiantiaokeji.com/admin/index.htm";

    @RequestMapping("/cookie")
    public ModelAndView cookie(HttpServletRequest request, HttpServletResponse response , Model model) throws IOException{

        ModelAndView mav = new ModelAndView();

        Cookie cookie = new Cookie("test","smguinaiani");
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(DEFAULT_LOGIN_SUCCESS_URL);
        mav.setViewName("admin/test/cookie");

        return mav;
    }


}
