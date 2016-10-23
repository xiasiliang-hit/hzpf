package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import com.line.bqxd.platform.client.dataobject.query.UserConcurRelationQueryDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.UserConcurRelationDAO;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class UserConcurRelationDAOImpl extends SimpleDAOImpl implements UserConcurRelationDAO<UserConcurRelationDO> {

    @Override
    public Map<String, Long> selectStatistics(UserConcurRelationQueryDO queryDO) {
        if (queryDO.getConcurPlanId() <= 0) {
            throw new IllegalArgumentException("selectStatistics argument illegal concurPlanId=" + queryDO.getConcurPlanId());

        }
        return (Map<String, Long>) getSqlMapClientTemplate().queryForObject(this.getSqlMapNameSpacePrefix() + ".selectStatistics", queryDO);
    }



    @Override
    public Long countByQuery(DBBaseQueryDO queryDO) {
        if (queryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        return  (Long)getSqlMapClientTemplate().queryForObject(getSqlMapNameSpacePrefix()+".countByQuery", queryDO);
    }

    @Override
    public boolean setStatusNormally(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("setStatusNormally id less than zero");
        }
        int result = getSqlMapClientTemplate().update(getSqlMapNameSpacePrefix()+".setStatusNormally", id);
        return result > 0 ? true : false;
    }

    @Override
    public Long statisticsRatio(DBBaseQueryDO queryDO) {
        if (queryDO == null) {
            throw new NullPointerException("DAO countByQuery object null");
        }
        Object o=getSqlMapClientTemplate().queryForObject(getSqlMapNameSpacePrefix()+".statisticsRatio", queryDO);
        if(o==null){
            return 0L;
        }else{
            return (Long) o;
        }
    }


}
