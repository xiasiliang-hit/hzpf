package com.line.bqxd.platform.controller.admin;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.dataobject.ConcurPlanDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.client.dataobject.query.UserQueryDO;
import com.line.bqxd.platform.controller.admin.domain.ConcurPlanVO;
import com.line.bqxd.platform.controller.admin.domain.UserConcurRelationVO;
import com.line.bqxd.platform.dao.ConcurPlanDAO;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.page.PageHandler;
import com.line.bqxd.platform.page.PageResult;
import com.line.bqxd.platform.page.PageWrap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/concur")
public class ConcurManagerController {
    private static Logger logger = LoggerFactory.getLogger(ConcurManagerController.class);

    @Resource
    private ConcurPlanDAO<ConcurPlanDO> concurPlanDAO;



    @Resource
    private UserManager userManager;

    @Resource
    private ConcurManager concurManager;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        List<ConcurPlanDO> list = concurPlanDAO.selectAll();
        if (CollectionUtils.isEmpty(list)) {

        } else {
            List<ConcurPlanVO> rtList = new ArrayList<ConcurPlanVO>();
            for (ConcurPlanDO concurPlanDO : list) {
                ConcurPlanVO planVO = new ConcurPlanVO();
                BeanUtils.copyProperties(concurPlanDO, planVO);
                planVO.setQuantity(concurManager.getConcurPlanSumUserQuantity(concurPlanDO.getId()));
                //planVO.setSubUserQuantity(concurManager.getConcurPlanSumSubUserQuantity(concurPlanDO.getId()));
                rtList.add(planVO);
            }
            mav.addObject("list", rtList);
        }


        mav.setViewName("admin/concur/list");
        return mav;
    }

    @RequestMapping("/view")
    public ModelAndView detail(HttpServletRequest request, Model model) {
        String concurId = request.getParameter("concurId");
        ConcurPlanDO concurPlanDO = (ConcurPlanDO) concurPlanDAO.selectById(Long.parseLong(concurId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("concurPlanDO", concurPlanDO);
        mav.setViewName("admin/concur/detail");

        return mav;
    }
/*
    @RequestMapping("/groupList")
    public ModelAndView croupList(HttpServletRequest request, Model model) {

        String concurId = request.getParameter("concurId");


        ModelAndView mav = new ModelAndView();
        //concurPlanGroupQueryDO.setGroupName();
        List<ConcurPlanGroupDO> list =null;
        if (CollectionUtils.isEmpty(list)) {

        } else {
            List<Long> ids = new ArrayList<Long>();
            for (ConcurPlanGroupDO groupDO : list) {
                ids.add(groupDO.getHeadUserId());
            }
            List<UserDO> userList = userManager.selectByIdList(ids);
            Map<Long, UserDO> map = new HashMap<Long, UserDO>();
            for (UserDO userDO : userList) {
                map.put(userDO.getUserId(), userDO);
            }
            List<ConcurGroupVO> rtList = new ArrayList<ConcurGroupVO>();

            for (ConcurPlanGroupDO groupDO : list) {
                ConcurGroupVO groupVO = new ConcurGroupVO();
                BeanUtils.copyProperties(groupDO, groupVO);
                UserDO userDO = map.get(groupDO.getHeadUserId());
                if (userDO != null) {
                    groupVO.setUserName(userDO.getUserName());
                    groupVO.setNickName(userDO.getNickName());
                    if (StringUtils.isBlank(groupVO.getGroupBackUrl())) {
                        groupVO.setGroupBackUrl(userDO.getHeadImgurl());
                    }
                }
                groupVO.setQuantity(concurManager.getConcurPlanGroupSumUserQuantity(groupDO.getConcurPlanId(), groupDO.getId()));
                groupVO.setSubUserQuantity(concurManager.getConcurPlanGroupSumSubUserQuantity(groupDO.getConcurPlanId(), groupDO.getId()));

                rtList.add(groupVO);
            }
            mav.addObject("list", rtList);
        }
        mav.addObject("concurId",concurId);
        mav.setViewName("admin/concur/groupList");

        return mav;
    }


    @RequestMapping("/groupView")
    public ModelAndView groupView(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView();
        String concurGroupId = request.getParameter("concurGroupId");
        String concurId = request.getParameter("concurId");
        if (StringUtils.isNotBlank(concurGroupId)) {


            mav.addObject("action", "update");
        } else {
            mav.addObject("action", "add");
        }
        mav.addObject("concurId", concurId);
        mav.setViewName("admin/concur/groupView");

        return mav;
    }


    @RequestMapping("/groupModified")
    public ModelAndView groupModified(ConcurPlanGroupDO concurPlanGroupDO, HttpServletRequest request, Model model) {

        String validatorStr = validatorGroup(concurPlanGroupDO);
        ModelAndView modelAndView = new ModelAndView();

        if(StringUtils.isNotBlank(validatorStr)){

            modelAndView.addObject("concurPlanGroupDO", concurPlanGroupDO);
            modelAndView.addObject("error", validatorStr);
            if (concurPlanGroupDO.getId() > 0) {
                modelAndView.addObject("action", "update");
            } else {
                modelAndView.addObject("action", "add");
            }
            modelAndView.addObject("concurId", concurPlanGroupDO.getConcurPlanId());

            modelAndView.setViewName("/admin/concur/groupView");
            return modelAndView;
        }


        modelAndView.setViewName("redirect:groupList.htm?concurId=" + concurPlanGroupDO.getConcurPlanId());

        return modelAndView;
    }

    private String validatorGroup(ConcurPlanGroupDO groupDO){
        if (StringUtils.isBlank(groupDO.getGroupName())){
            return "分组名称不能够为空";
        }

        if(StringUtils.isBlank(groupDO.getGroupDesc())){
            return "分组描诉不能够为空";
        }
        if(StringUtils.isNotBlank(groupDO.getGroupBackUrl())&&!CommonValidatorUtils.IsUrl(groupDO.getGroupBackUrl())){
            return "分组背景图片地址验证错误";
        }
        if(groupDO.getConcurPlanId()<=0){
            return "请选择互助计划";
        }
        if(groupDO.getHeadUserId()<=0){
            return "请选择互助计划小组组长";
        }

        UserDO userDO = userManager.selectByUserId(groupDO.getHeadUserId());
        if(userDO==null){
            return "选择互助计划小组组长不存在";
        }
        return null;
    }
*/

    @RequestMapping("/groupUserList")
    public ModelAndView groupUserView(UserConcurRelationQueryDO relationQueryDO, @RequestParam(value = "queryJson",required=false) String queryJson,HttpServletRequest request, Model model) {
        if(StringUtils.isNotBlank(queryJson)){
            UserConcurRelationQueryDO userQueryDOJson= JSON.parseObject(queryJson,UserConcurRelationQueryDO.class);
            userQueryDOJson.setPageNum(relationQueryDO.getPageNum());
            relationQueryDO=userQueryDOJson;
        }
        String userName = request.getParameter("userName");

        ModelAndView mav = new ModelAndView();
        mav.addObject("query", relationQueryDO);
        mav.addObject("userName", userName);
        if (StringUtils.isNotBlank(userName)) {
            UserQueryDO userQueryDO = new UserQueryDO();
            userQueryDO.setUserName(userName);
            userQueryDO.initStartNum();
            List<UserDO> list = userManager.selectByQueryPage(userQueryDO);
            if (list == null || list.size() <= 0) {
                mav.setViewName("admin/concur/groupUserList");
                return mav;
            } else {
                List<Long> ids = new ArrayList<Long>();
                for (UserDO userDO : list) {
                    ids.add(userDO.getUserId());
                }
                relationQueryDO.setUserIds(ids);
            }
        }

        PageWrap pageWrap = new PageWrap();
        PageResult result = pageWrap.getPageResult(relationQueryDO, new PageHandler<UserConcurRelationQueryDO>() {
            @Override
            public List<? extends Base> getPageList(UserConcurRelationQueryDO queryDO) {
                return concurManager.selectByQueryPage(queryDO);
            }

            @Override
            public long getPageCount(UserConcurRelationQueryDO queryDO) {
                return concurManager.countByQueryPage(queryDO);
            }
        });
        if (result != null && result.getModle() != null && result.getModle().size() > 0) {
            List<UserConcurRelationDO> list = result.getModle();
            List<Long> ids = new ArrayList<Long>();
            for (UserConcurRelationDO relationDO : list) {
                ids.add(relationDO.getUserId());
            }
            List<UserDO> userList = userManager.selectByIdList(ids);
            Map<Long, UserDO> map = new HashMap<Long, UserDO>();
            for (UserDO userDO : userList) {
                map.put(userDO.getUserId(), userDO);
            }
            List<UserConcurRelationVO> rtList = new ArrayList<UserConcurRelationVO>();

            for (UserConcurRelationDO relationDO : list) {
                UserConcurRelationVO relationVO = new UserConcurRelationVO();
                BeanUtils.copyProperties(relationDO, relationVO);
                UserDO userDO = map.get(relationDO.getUserId());
                if (userDO != null) {
                    //relationVO.setUserName(userDO.getUserName());
                    //relationVO.setNickName(userDO.getNickName());

                }
                rtList.add(relationVO);
            }
            result.setModle(rtList);
        }
        mav.addObject("page", result);
        if(StringUtils.isNotBlank(queryJson)) {
            result.setQueryJson(URLEncoder.encode(queryJson));
        }else{
            result.setQueryJson(URLEncoder.encode(JSON.toJSONString(relationQueryDO)));
        }
        mav.setViewName("admin/concur/groupUserList");

        return mav;
    }

    @RequestMapping("/delGroupUser")
    public ModelAndView delGroupUser(HttpServletRequest request, Model model) {
        String idstr = request.getParameter("id");
        String concurId = request.getParameter("concurId");
        long id = 0;

        try {
            id = Long.parseLong(idstr);
        } catch (Exception e) {

        }
        concurManager.delUserRelation(id);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:groupUserList.htm?concurPlanGroupId=" + concurId);

        return mav;
    }
}
