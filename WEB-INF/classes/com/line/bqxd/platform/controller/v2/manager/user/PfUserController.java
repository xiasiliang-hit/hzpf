package com.line.bqxd.platform.controller.v2.manager.user;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.ConcurPlanQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.v2.manager.vo.ClaimApplyVO;
import com.line.bqxd.platform.controller.v2.manager.vo.UserConcurRelationVO;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/21.
 */
@Controller
@RequestMapping("/v2/manager/user")
public class PfUserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PfUserController.class);

    @Autowired
    private ClaimApplyManager claimApplyManager;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private UserManager userManager;


    @RequestMapping(value = "/list")
    public ModelAndView list(UserConcurRelationQueryDO userConcurRelationQueryDO, @RequestParam(value = "queryJson", required = false) String queryJson, HttpServletRequest request, Model model) throws Exception {
        if (StringUtils.isNotBlank(queryJson)) {
            UserConcurRelationQueryDO userQueryDOJson = JSON.parseObject(queryJson, UserConcurRelationQueryDO.class);
            userQueryDOJson.setPageNum(userConcurRelationQueryDO.getPageNum());
            userConcurRelationQueryDO = userQueryDOJson;
        }

        ModelAndView mav = new ModelAndView();
        AdminSession adminSession = this.getAdminSessionUserInfo(request);
        PageResult result = null;
        final long concurId = getConcurId(adminSession);
        if (concurId > 0) {
            userConcurRelationQueryDO.setConcurPlanId(concurId);
            PageWrap pageWrap = new PageWrap();

            result = pageWrap.getPageResult(userConcurRelationQueryDO, new PageHandler<UserConcurRelationQueryDO>() {
                @Override
                public List<? extends Base> getPageList(UserConcurRelationQueryDO queryDO) {
                    return conver(concurManager.selectByQueryPage(queryDO));
                }

                @Override
                public long getPageCount(UserConcurRelationQueryDO queryDO) {
                    return concurManager.countByQueryPage(queryDO);
                }
            });
        }
        mav.addObject("page", result);
        if (result != null) {
            if (StringUtils.isNotBlank(queryJson)) {
                result.setQueryJson(URLEncoder.encode(queryJson));
            } else {
                if (userConcurRelationQueryDO != null) {
                    result.setQueryJson(URLEncoder.encode(JSON.toJSONString(userConcurRelationQueryDO)));
                }
            }
        }
        mav.setViewName("v2/manager/user/list");
        return mav;

    }


    private List<UserConcurRelationVO> conver(List<UserConcurRelationDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> ids = new ArrayList<Long>();
        for (UserConcurRelationDO userConcurRelationDO : list) {
            if (!ids.contains(userConcurRelationDO.getUserId())) {
                ids.add(userConcurRelationDO.getUserId());
            }
        }
        Map<Long, UserDO> userMap = userManager.selectByUserIds(ids);
        List<UserConcurRelationVO> rtList = new ArrayList<UserConcurRelationVO>();
        for (UserConcurRelationDO userConcurRelationDO : list) {
            UserConcurRelationVO vo = new UserConcurRelationVO();
            BeanUtils.copyProperties(userConcurRelationDO, vo);
            UserDO userDO = userMap.get(userConcurRelationDO.getUserId());
            if (userDO != null) {
                vo.setCard(userDO.getCard());
                vo.setMobile(userDO.getMobile());
                vo.setUserName(userDO.getUserName());
                vo.setHeadImgurl(userDO.getHeadImgurl());
            }
            rtList.add(vo);
        }
        return rtList;
    }


    private long getConcurId(AdminSession adminSession) {
        PfWeixinAuthDO pfWeixinAuthDO = pfWeixinAuthManager.selectByCorpid(adminSession.getCorpId());
        if (pfWeixinAuthDO == null) {
            return 0;
        }
        long pfAppId = pfWeixinAuthDO.getId();
        ConcurPlanQueryDO queryDO = new ConcurPlanQueryDO();
        queryDO.setPfAppId(pfAppId);
        List<ConcurPlanDO> list = concurManager.selectByQueryPage(queryDO);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return list.get(0).getId();
        }
    }

}
