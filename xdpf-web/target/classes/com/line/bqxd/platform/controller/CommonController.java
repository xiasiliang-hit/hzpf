package com.line.bqxd.platform.controller;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.controller.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huangjianfei on 16/6/6.
 */
@Controller
public class CommonController extends BaseController {

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/index");
        return mav;
    }


    @RequestMapping("/test/preview")
    public ModelAndView preview(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();

        mav.addObject("previewContent",request.getParameter("previewContent"));
        mav.addObject("previewTitle",request.getParameter("previewTitle"));

        String previewHaveAdvert=request.getParameter("previewHaveAdvert");
        mav.addObject("previewHaveAdvert",previewHaveAdvert);

        if(StringUtils.isNotBlank(previewHaveAdvert)&&previewHaveAdvert.equalsIgnoreCase("true")){
            String previewAdvertPic=request.getParameter("previewAdvertPic");
            String previewAdvertTitle1=request.getParameter("previewAdvertTitle1");
            String previewAdvertTitle2=request.getParameter("previewAdvertTitle2");
            String previewAdvertButtonText=request.getParameter("previewAdvertButtonText");
            String previewAdvertUrl=request.getParameter("previewAdvertUrl");
            mav.addObject("previewAdvertPic",previewAdvertPic);
            mav.addObject("previewAdvertTitle1",previewAdvertTitle1);
            mav.addObject("previewAdvertTitle2",previewAdvertTitle2);
            mav.addObject("previewAdvertButtonText",previewAdvertButtonText);
            mav.addObject("previewAdvertUrl",previewAdvertUrl);
        }

        mav.setViewName("/test/preview");
        return mav;
    }


}
