package com.line.bqxd.platform.controller.env;

import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.env.EnvManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/env")
public class EnvController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(EnvController.class);

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @Autowired
    private EnvManager envManager;

    @RequestMapping(value = "/getData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void getData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        //TODO 需要增加安全判断
        String data = "";
        if (envManager.isStartPfData()) {
            String appid = request.getParameter("appid");
            String type = request.getParameter("type");
            if (StringUtils.isBlank(appid)) {
                if ("componentVerifyTicket".equals(type)) {
                    data = pfApplication.getComponentVerifyTicket();
                } else if ("componentAccessToken".equals(type)) {
                    data = pfApplication.getComponentAccessToken();
                } else if ("preAuthCode".equals(type)) {
                    data = pfApplication.getPreAuthCode();
                }
            } else {
                GlobalObject globalObject = pfApplication.getGlobalObjectByAppid(appid);
                if (globalObject != null) {
                    if ("accessToken".equals(type)) {
                        data = globalObject.getAccessToken();
                    } else if ("ticket".equals(type)) {
                        data = globalObject.getTicket();
                    }
                }
            }
        } else {
            logger.warn("local not support env data");
        }
        response.getWriter().write(data);
    }


}
