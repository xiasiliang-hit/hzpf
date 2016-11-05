package com.line.bqxd.platform.controller.safety.pay;

import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.common.utils.NginxTool;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.wxpay.RequestHandler;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.manager.wxpay.impl.WXPayManagerImpl;
import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/safety/pay")
public class WxPayJsTestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WxPayJsTestController.class);

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private WXPayManager wxPayManager;

    @RequestMapping("/test2")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        //BizResult<WXPayResult> result = tradeManager.tradeCreate(1, sessionUserInfo.getUserId(),1, sessionUserInfo.getOpenid());
       // String openId="ot3tXs0AZNShis5r4mG6muF-5hEc";
        String openId=sessionUserInfo.getOpenid();
        long userId=95;
        BizResult<WXPayResult> result = tradeManager.fillCash(userId,0, 50000, openId, NginxTool.getIpByNginx(request), BizFeeType.CONCURFEE.getValue(),null);
/*
        String fullPath= this.getFullPath(request);.
        Map data = Sign.sign(this.getTicket(), fullPath);

        this.setSignView(mav,data.get("timestamp").toString(),data.get("nonceStr").toString(),data.get("signature").toString());
        */
        if (result.getModel() != null) {
            WXPayResult wxPayResult = result.getModel();
            if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode()) && WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                String appid=wxPayResult.getAppId();
                String nonce_str =wxPayResult.getNonceStr();
                String signType="MD5";
                String packageStr="prepay_id="+wxPayResult.getPrepayId();
                String timeStamp =String.valueOf(System.currentTimeMillis()/1000);
                RequestHandler requestHandler =new RequestHandler();
                requestHandler.setParameter("appId", appid);
                requestHandler.setParameter("nonceStr", nonce_str);
                requestHandler.setParameter("signType", signType);
                requestHandler.setParameter("package",packageStr);
                requestHandler.setParameter("timeStamp", timeStamp);

                String sign = PayUtil.createWXPaySign("UTF-8", requestHandler.getParameters(),((WXPayManagerImpl)wxPayManager).getWxPaySecret(0));

                mav.addObject("appId",appid);
                mav.addObject("nonceStr",nonce_str);
                mav.addObject("signType",signType);
                mav.addObject("package",packageStr);
                mav.addObject("timeStamp",timeStamp);
                mav.addObject("sign",sign);
            }
        }


        mav.setViewName("safety/pay/test");
        return mav;
    }




}
