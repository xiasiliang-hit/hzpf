package com.line.bqxd.platform.controller.admin;

import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.manager.comments.CommentsManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
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
import java.util.List;

/**
 * Created by huangjianfei on 16/4/15.
 */

@Controller
@RequestMapping("/admin/concur")
public class CommentsController {
    private static Logger logger = LoggerFactory.getLogger(CommentsController.class);

    private final static String ACTIVITY_URL="http://www.xiongdihuzhu.com/concur/detail.htm";

    @Autowired
    private CommentsDAO<CommentsDO> commentsDAO;

    @Autowired
    private CommentsManager commentsManager;

    @Autowired
    private ConcurManager concurManager;

    @RequestMapping("/addComments")
    public ModelAndView addComments(CommentsDO commentsDO, HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:commentsList");
        if(commentsDO==null||StringUtils.isBlank(commentsDO.getNickName())||StringUtils.isBlank(commentsDO.getContent())||StringUtils.isBlank(commentsDO.getHeadImgUrl())){
            modelAndView.addObject("error","提交参数异常");
        }else {
            long id = commentsDAO.insert(commentsDO);
        }

        return modelAndView;

    }

    @RequestMapping("/delComments")
    public ModelAndView delComments(@RequestParam(value = "id") long commentId,HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:commentsList");
        if (commentId<=0) {
            modelAndView.addObject("error","提交参数异常");
        }else{
            commentsDAO.delete(commentId);
        }
        return modelAndView;

    }

    @RequestMapping("/commentsList")
    public ModelAndView commentsList(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("/admin/concur/commentsList");
        String id=request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            modelAndView.addObject("comments",commentsDAO.selectById(Integer.parseInt(id)));
        }
        CommentsQueryDO commentsQueryDO = new CommentsQueryDO();
        commentsQueryDO.setPageSize(50);
        commentsQueryDO.setStatus(1);
        List<CommentsDO> list=commentsDAO.selectByQuery(commentsQueryDO);
        modelAndView.addObject("list",list);
        return modelAndView;

    }

    @RequestMapping("/commentsView")
    public ModelAndView commentsView(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("/admin/concur/commentsView");
        String id=request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            modelAndView.addObject("update" ,"true");
            modelAndView.addObject("comments",commentsDAO.selectById(Integer.parseInt(id)));
        }
        //modelAndView.addObject("outBizId",concurManager.getDefaultConcurPlanId());
        return modelAndView;

    }

   @RequestMapping("/updateComments")
   public ModelAndView updateComments(CommentsDO commentsDO, HttpServletRequest request, Model model){
        ModelAndView modelAndView = new ModelAndView("redirect:commentsList");
        if(commentsDO==null||commentsDO.getId()<=0||StringUtils.isBlank(commentsDO.getNickName())||StringUtils.isBlank(commentsDO.getContent())||StringUtils.isBlank(commentsDO.getHeadImgUrl())){
            modelAndView.addObject("error","提交参数异常");
        }else {

            commentsDAO.update(commentsDO);
        }
        return modelAndView;
   }

    @RequestMapping("/commentsRefresh")
    public ModelAndView commentsRefresh(HttpServletRequest request, Model model){
        ModelAndView modelAndView = new ModelAndView("redirect:commentsList");
        commentsManager.reflush();
        return modelAndView;
    }


}
