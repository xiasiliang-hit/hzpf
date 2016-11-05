package com.line.bqxd.platform.manager.comments.impl;

import com.line.bqxd.platform.client.constant.BizType;
import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.manager.comments.CommentsManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/27.
 */
public class CommentsManagerImpl implements CommentsManager {
    private static Logger logger = LoggerFactory.getLogger(CommentsManagerImpl.class);

    @Autowired
    private CommentsDAO<CommentsDO> commentsDAO;

    @Autowired
    private ConcurManager concurManager;

    private Map<Long ,List<CommentsDO>> cacheMap=new HashMap<Long ,List<CommentsDO>>();

    private List<CommentsDO> list = new ArrayList<CommentsDO>();

    public void init() {
        //这里暂时只做一种重疾缓存
        //TODO 待处理
        /*
        String concurPlanId=concurManager.getDefaultConcurPlanId();

        CommentsQueryDO commentsQueryDO = new CommentsQueryDO();
        commentsQueryDO.setPageSize(10);
        commentsQueryDO.setBizType(BizType.CONCUR.getValue());
        commentsQueryDO.setOutBizId(concurPlanId);
        cacheMap.put(Long.parseLong(concurPlanId),commentsDAO.selectByQuery(commentsQueryDO));
        */

    }

    @Override
    public List<CommentsDO> getCommentsListByCache(long concurPlanId) {
        return cacheMap.get(concurPlanId);
    }

    @Override
    public void reflush() {
        logger.warn("CommentsManager reflsh start");
        init();
        logger.warn("CommentsManager reflsh end");

    }


}
