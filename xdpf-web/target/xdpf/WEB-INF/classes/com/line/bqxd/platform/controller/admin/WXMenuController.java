package com.line.bqxd.platform.controller.admin;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wxmenu.WXMenuManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
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
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/system")
public class WXMenuController {
    private static Logger logger = LoggerFactory.getLogger(WXMenuController.class);
    private static final   String MENU_PREFIX="{\"menu\":";
    private static final   String MENU_SUFFIX="}";
    @Autowired
    private WXMenuManager wxmenuManager;

    @RequestMapping("/menuView")
    public ModelAndView view(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        try {
            String menu = wxmenuManager.getMenu();
            if (StringUtils.isBlank(menu)) {
                mav.addObject("error", "微信菜单数据为空或者异常");
            } else {
                if(menu.length()>9){
                    menu=menu.substring(8,menu.length()-1);
                }
                mav.addObject("content", menu);
            }
        } catch (Exception e) {
            mav.addObject("error", "系统异常");
        }
        mav.setViewName("admin/menu/view");

        return mav;
    }

    @RequestMapping("/modifiyMenu")
    public ModelAndView modifiy(@RequestParam(value = "content", required = false) String content, HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(content)) {
            mav.addObject("error", "微信菜单数据为空");
        } else {
            BizResult bizResult = wxmenuManager.createMenu(content);
            mav.addObject("content", content);
            if (!bizResult.isSuccess()) {
                mav.addObject("error", bizResult.getFormatError());
            }else{
                mav.addObject("success", "true");

            }
        }
        mav.setViewName("admin/menu/view");
        return mav;
    }

}
