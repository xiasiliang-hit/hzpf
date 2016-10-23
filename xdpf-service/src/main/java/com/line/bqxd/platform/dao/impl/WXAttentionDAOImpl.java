package com.line.bqxd.platform.dao.impl;


import com.line.bqxd.platform.client.dataobject.CommentsDO;
import com.line.bqxd.platform.client.dataobject.WXAttentionDO;
import com.line.bqxd.platform.client.dataobject.query.CommentsQueryDO;
import com.line.bqxd.platform.dao.CommentsDAO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.WXAttentionDAO;
import org.apache.commons.lang.StringUtils;


/**
 * Created by huangjianfei on 16/5/10.
 */
public class WXAttentionDAOImpl extends SimpleDAOImpl implements WXAttentionDAO<WXAttentionDO> {


    @Override
    public boolean deleteByOpenId(String openid) {
        if (StringUtils.isBlank(openid)) {
            throw new IllegalArgumentException("deleteByOpenId openid is blank");
        }
        int result = getSqlMapClientTemplate().delete(getSqlMapNameSpacePrefix() + ".deleteByOpenId", openid);
        return result > 0 ? true : false;
    }


}
