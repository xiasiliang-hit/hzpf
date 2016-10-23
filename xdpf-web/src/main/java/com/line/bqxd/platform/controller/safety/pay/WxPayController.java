package com.line.bqxd.platform.controller.safety.pay;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeBillQueryDO;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.security.Sign;
import com.line.bqxd.platform.common.utils.NginxTool;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wxpay.RequestHandler;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.manager.wxpay.impl.WXPayManagerImpl;
import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/safety/pay")
public class WxPayController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private WXPayManager wxPayManager;

    @Autowired
    private UserPayManager userPayManager;

    @RequestMapping("/submitOrder")
    @ResponseBody
    public Result submitOrder(@RequestParam(value = "fee", required = false, defaultValue = "0") long fee, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        if (fee <= 0) {
            return Result.of(ResultEnum.PAY_FILL_CASH_FEE_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_FEE_FAIL.getDesc());
        }
        long feeFen = Long.parseLong(BizUtils.changeY2F(fee));
        BizResult<WXPayResult> result = tradeManager.fillCash(sessionUserInfo.getUserId(),0, feeFen, sessionUserInfo.getOpenid(), NginxTool.getIpByNginx(request) ,BizFeeType.CONCURFEE.getValue(),null);
        if (result.getModel() != null) {
            WXPayResult wxPayResult = result.getModel();
            if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode()) && WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                String appid = wxPayResult.getAppId();
                String nonce_str = wxPayResult.getNonceStr();
                String signType = "MD5";
                String packageStr = "prepay_id=" + wxPayResult.getPrepayId();
                String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.setParameter("appId", appid);
                requestHandler.setParameter("nonceStr", nonce_str);
                requestHandler.setParameter("signType", signType);
                requestHandler.setParameter("package", packageStr);
                requestHandler.setParameter("timeStamp", timeStamp);

                String sign = PayUtil.createWXPaySign("UTF-8", requestHandler.getParameters(), ((WXPayManagerImpl) wxPayManager).getWxPaySecret(0));
                Map<String, String> map = new HashMap<String, String>();
                map.put("appId", appid);
                map.put("nonceStr", nonce_str);
                map.put("signType", signType);
                map.put("package", packageStr);
                map.put("timeStamp", timeStamp);
                map.put("sign", sign);
                return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), map);
            } else {
                logger.warn("user submet order fail,user={},{}", sessionUserInfo.getUserId(), wxPayResult);
                if (!WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode())) {
                    return Result.of(ResultEnum.PAY_FILL_CASH_WX_COMM_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_COMM_FAIL.getDesc());

                } else if (!WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                    return Result.of(ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getDesc());

                } else {
                    return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
                }
            }
        } else {
            logger.warn("user submet order fail,user={},result is null", sessionUserInfo.getUserId());
            return Result.of(ResultEnum.SYSERROR.getCode(), ResultEnum.SYSERROR.getDesc());
        }
    }


    @RequestMapping("/openOrder")
    public ModelAndView openOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        //setSignView(request, mav);
        mav.setViewName("safety/pay/openOrder");
        return mav;
    }


    @RequestMapping("/billList")
    public ModelAndView billList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        String listType=request.getParameter("listType");
        String pageNum=request.getParameter("pageNum");

        ModelAndView mav = new ModelAndView();
        //setSignView(request, mav);
        UserTradeBillQueryDO queryDO = new UserTradeBillQueryDO();
        queryDO.setListType(listType);
        queryDO.setUserId(sessionUserInfo.getUserId());
        if (StringUtils.isBlank(pageNum)) {
            queryDO.initStartNum();
        } else {
            queryDO.setPageNum(Integer.parseInt(pageNum));
        }
        PageWrap pageWrap= new PageWrap();
        PageResult result = pageWrap.getPageResult(queryDO, new PageHandler<UserTradeBillQueryDO>() {
            @Override
            public List<? extends Base> getPageList(UserTradeBillQueryDO queryDO) {
                return userPayManager.getTradeBillListByPage(queryDO);
            }
            @Override
            public long getPageCount(UserTradeBillQueryDO queryDO) {
                return userPayManager.countByQuery(queryDO);
            }
        });
        mav.addObject("page",result);
        mav.setViewName("safety/pay/openOrder");
        return mav;
    }

}
