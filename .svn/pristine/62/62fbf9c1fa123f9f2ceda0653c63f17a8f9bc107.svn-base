package com.line.bqxd.platform.controller.admin;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.admin.AdminSession;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.constant.UserType;
import com.line.bqxd.platform.client.dataobject.UserAdminDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.dao.UserAdminDAO;
import com.line.bqxd.platform.login.SessionConstants;
import com.line.bqxd.platform.manager.RunEnv;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final static String DEFAULT_LOGIN_SUCCESS_URL="http://www.xiongdihuzhu.com/admin/index.htm";

    @Autowired
    private UserManager userManager;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private RunEnv runEnv;

    private final static String DELETE_OPENId_ARRAY="o8GVywuhVYiIjQEF9h9pJrJZS4IM,o8GVywgUJwGv3HPqO3cqHwsi_1_o,o8GVywvd9OCYurEntURrCQI7x34o,o8GVywhD3t5oLxbTDMkos0ZGSEj4";

    @RequestMapping("/list")
    public ModelAndView list(UserQueryDO userQueryDO, @RequestParam(value = "queryJson",required=false) String queryJson,HttpServletRequest request, Model model) {
        if(StringUtils.isNotBlank(queryJson)){
            UserQueryDO userQueryDOJson=JSON.parseObject(queryJson,UserQueryDO.class);
            userQueryDOJson.setPageNum(userQueryDO.getPageNum());
            userQueryDO=userQueryDOJson;
        }
        ModelAndView mav = new ModelAndView();
        PageWrap pageWrap= new PageWrap();
        PageResult result = pageWrap.getPageResult(userQueryDO, new PageHandler<UserQueryDO>() {
            @Override
            public List<? extends Base> getPageList(UserQueryDO queryDO) {
                return userManager.selectByQueryPage(queryDO);
            }
            @Override
            public long getPageCount(UserQueryDO queryDO) {
                return userManager.countByQueryPage(queryDO);
            }
        });
        mav.addObject("page",result);
        if (StringUtils.isNotBlank(queryJson)) {
            result.setQueryJson(URLEncoder.encode(queryJson));
        } else {
            result.setQueryJson(URLEncoder.encode(JSON.toJSONString(userQueryDO)));
        }
        mav.setViewName("admin/user/list");

        return mav;
    }

    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser( @RequestParam(value = "userId",required=false) long userId,HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        if(isCanUserDel(userId)) {
            UserQueryDO userQueryDO = new UserQueryDO();

            userQueryDO.setPageSize(1000);
            List<UserDO> subUserList = userManager.selectByQueryPage(userQueryDO);
            List<Long> deleteUserIds = new ArrayList<Long>();
            deleteUserIds.add(userId);
            boolean userResult = userManager.delete(userId);
            logger.warn("delete user userId={},result={}", userId, userResult);

            if (!CollectionUtils.isEmpty(subUserList)) {
                for (UserDO subUserDO : subUserList) {
                    deleteUserIds.add(subUserDO.getUserId());
                    boolean result = userManager.delete(subUserDO.getUserId());
                    logger.warn("delete subUser,userId= subUserId={},result={}", userId, subUserDO.getUserId(), result);
                }
            }

            UserConcurRelationQueryDO userConcurRelationQueryDO = new UserConcurRelationQueryDO();

            userConcurRelationQueryDO.setUserIds(deleteUserIds);
            userConcurRelationQueryDO.setPageSize(deleteUserIds.size());
            List<UserConcurRelationDO> userRelationList = concurManager.selectByQueryPage(userConcurRelationQueryDO);
            if (!CollectionUtils.isEmpty(userRelationList)) {
                for (UserConcurRelationDO userConcurRelationDO : userRelationList) {
                    boolean result = concurManager.delUserRelation(userConcurRelationDO.getId());
                    logger.warn("delete userRelation,parentUserId={}, id={},userId={},result={}", userId, userConcurRelationDO.getId(), userConcurRelationDO.getUserId(), result);

                }
            }
        }else{

        }
        mav.setViewName("redirect:/admin/user/list");

        return mav;
    }

    //只有测试环境和固定用户可以做真正的物理删除
    private boolean isCanUserDel(long userId){
        if(runEnv.isOnline()){
            UserDO userDO=userManager.selectByUserId(userId);
            if(DELETE_OPENId_ARRAY.indexOf(userDO.getOpenid())>-1){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
