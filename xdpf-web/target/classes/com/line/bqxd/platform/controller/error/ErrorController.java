package com.line.bqxd.platform.controller.error;

import com.line.bqxd.platform.controller.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/6/2.
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
    @RequestMapping("/404")
    public ModelAndView error404(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorType", "404");
        mav.setViewName("error");
        return mav;

    }

    @RequestMapping("/500")
    public ModelAndView error500(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorType", "500");
        mav.setViewName("error");
        return mav;

    }

    @RequestMapping("/common")
    public ModelAndView common(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        String errorType=request.getParameter("errorType");
        String errorMsg=request.getParameter("errorMsg");
        mav.addObject("errorMsg", errorMsg);
        mav.addObject("errorType", errorType);
        mav.setViewName("error");
        return mav;

    }
}
