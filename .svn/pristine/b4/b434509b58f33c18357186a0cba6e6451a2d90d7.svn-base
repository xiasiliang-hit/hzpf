package com.line.bqxd.platform.controller.v2.concur;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.manager.PayTestMapping;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/concur/")
public class Test2Controller extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(Test2Controller.class);

    private static final String AUTH_RETURN_URL = "http://pf.xiongdihuzhu.com/v2/manager/auth/show.htm";

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private RunEnv runEnv;

    @Autowired
    private PayTestMapping payTestMapping;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();


        mav.setViewName("v2/concur/test");
        return mav;

    }

    private String buiderAuthUrl() {
        StringBuilder sb = new StringBuilder(PfApplication.PF_AUTHORIZER_URL);
        sb.append(pfApplication.getPfAppId());
        //component_appid=xxxx&pre_auth_code=xxxxx&redirect_uri=xxxx
        sb.append("&");
        sb.append("pre_auth_code=");
        sb.append(pfApplication.getPreAuthCode());
        sb.append("&");
        sb.append("redirect_uri=");
        sb.append(AUTH_RETURN_URL);
        return sb.toString();
    }

    private String buiderApiQueryAuthUrl() {
        StringBuilder sb = new StringBuilder(PfApplication.PF_API_QUERY_AUTH_URL);
        sb.append(pfApplication.getComponentAccessToken());

        return sb.toString();
    }


    private String buiderApiQueryAuthPostData(String authCode) {
        StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("\"component_appid\":\"" + pfApplication.getPfAppId() + "\",");
        sb.append("\"authorization_code\":\"" + authCode + "\"");
        sb.append('}');
        return sb.toString();

    }

    private String buiderApiGetAuthorizerInfoUrl() {
        StringBuilder sb = new StringBuilder(PfApplication.PF_GET_AUTHORIZER_INFO_URL);
        sb.append(pfApplication.getComponentAccessToken());

        return sb.toString();
    }

    private String buiderApiGetAuthorizerInfoPostData(String authAppid) {
        StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("\"component_appid\":\"" + pfApplication.getPfAppId() + "\",");
        sb.append("\"authorizer_appid\":\"" + authAppid + "\"");
        sb.append('}');
        return sb.toString();

    }

    private String getAuthAppid(JSONObject jsonObject) {
        if (jsonObject != null) {
            JSONObject jsonObject1 = jsonObject.getJSONObject("authorization_info");
            if (jsonObject1 != null) {
                return jsonObject1.getString("authorizer_appid");
            }
        }

        return null;
    }


}
