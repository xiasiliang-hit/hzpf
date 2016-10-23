package com.line.bqxd.platform.manager.comments;

import com.line.bqxd.platform.client.dataobject.CommentsDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/27.
 */
public interface CommentsManager {


    public List<CommentsDO> getCommentsListByCache(long concurPlanId);

    public void reflush();
}
