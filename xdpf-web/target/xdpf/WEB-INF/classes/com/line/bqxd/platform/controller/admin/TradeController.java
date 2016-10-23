package com.line.bqxd.platform.controller.admin;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.UserTradeFillDO;
import com.line.bqxd.platform.client.dataobject.query.UserTradeFillQueryDO;
import com.line.bqxd.platform.controller.admin.domain.UserTradeFillVO;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/trade")
public class TradeController {
    private static Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final static String DEFAULT_LOGIN_SUCCESS_URL="http://www.xiantiaokeji.com/admin/index.htm";

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private UserManager userManager;



    @Autowired
    private WXPayManager wxPayManager;

    @RequestMapping("/fillFeeList")
    public ModelAndView fillFeeList(UserTradeFillQueryDO userTradeFillQueryDO, @RequestParam(value = "queryJson",required=false) String queryJson, HttpServletRequest request, HttpServletResponse response , Model model) throws IOException{
        if(StringUtils.isNotBlank(queryJson)){
            UserTradeFillQueryDO userQueryDOJson= JSON.parseObject(queryJson,UserTradeFillQueryDO.class);
            userQueryDOJson.setPageNum(userTradeFillQueryDO.getPageNum());
            userTradeFillQueryDO=userQueryDOJson;
        }

        ModelAndView mav = new ModelAndView();
        PageWrap pageWrap= new PageWrap();
        PageResult result = pageWrap.getPageResult(userTradeFillQueryDO, new PageHandler<UserTradeFillQueryDO>() {
            @Override
            public List<? extends Base> getPageList(UserTradeFillQueryDO queryDO) {
                return tradeManager.selectTradeFillByQueryPage(queryDO);
            }
            @Override
            public long getPageCount(UserTradeFillQueryDO queryDO) {
                return tradeManager.countTradeFillByQueryPage(queryDO);
            }
        });

        injectUser(result);
        mav.addObject("page",result);

        if (StringUtils.isNotBlank(queryJson)) {
            result.setQueryJson(URLEncoder.encode(queryJson));
        } else {
            result.setQueryJson(URLEncoder.encode(JSON.toJSONString(userTradeFillQueryDO)));
        }
        mav.setViewName("admin/trade/fillFeeList");

        return mav;
    }

    private void injectUser(PageResult result){
        List<UserTradeFillDO> list=result.getModle();
        if(!CollectionUtils.isEmpty(list)){
            List<Long> userIds=new ArrayList<Long>();
            for(UserTradeFillDO  tradeFillDO: list){
                userIds.add(tradeFillDO.getUserId());
            }
            Map<Long ,UserDO> map=userManager.selectByUserIds(userIds);
            List<UserTradeFillVO> viewList=new ArrayList<UserTradeFillVO>();
            for(UserTradeFillDO  tradeFillDO: list){
                UserTradeFillVO vo = new UserTradeFillVO();
                BeanUtils.copyProperties(tradeFillDO, vo);
                UserDO userDO=map.get(tradeFillDO.getUserId());
                if(userDO!=null) {
                    vo.setNickName(userDO.getNickName());
                    vo.setUserName(userDO.getUserName());
                    vo.setHeadImgUrl(userDO.getHeadImgurl());
                }
                viewList.add(vo);

            }
            result.setModle(viewList);
        }
    }






    @RequestMapping("/unusualTradeDay")
    @ResponseBody
    public String unusualTradeDay(@RequestParam(value = "day",required=false) String day, HttpServletRequest request, HttpServletResponse response , Model model) throws IOException{
        if(StringUtils.isBlank(day)){
            return "day is empty";
        }
        wxPayManager.handleUnusualTrade(day);
        return "success";
    }

}
