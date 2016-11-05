package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.ConcurRemindDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.client.dataobject.query.ConcurRemindQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.dao.ConcurRemindDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class CommentsDAOImpl extends SimpleDAOImpl implements CommentsDAO<CommentsDO> {
    @Override
    public int countByQuery(CommentsQueryDO commentsQueryDO) {
        if (commentsQueryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix()+".countByQuery",commentsQueryDO);
        if(o!=null&&o instanceof Integer){
            return ((Integer)o).intValue();
        }else{

        }
        return 0;
    }
}
