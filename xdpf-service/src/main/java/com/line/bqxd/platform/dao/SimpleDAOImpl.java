package com.line.bqxd.platform.dao;

import com.line.bqxd.platform.client.common.BaseDAO;
import com.line.bqxd.platform.client.common.DBBaseQueryDO;
import com.line.bqxd.platform.common.BaseDAOImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/3.
 */
public class SimpleDAOImpl<T> extends BaseDAOImpl implements BaseDAO<T> {
    private String sqlMapNameSpacePrefix = null;

    @Override
    public long insert(T t) {
        if (t == null) {
            throw new NullPointerException("DAO insert object null");
        }
        Object object = getSqlMapClientTemplate().insert(sqlMapNameSpacePrefix + ".insert", t);
        if (object != null && object instanceof Long) {
            return (Long) object;
        }
        return 0;
    }

    @Override
    public boolean update(T t) {
        if (t == null) {
            throw new NullPointerException("DAO update object null");
        }
        int result = getSqlMapClientTemplate().update(sqlMapNameSpacePrefix+".update", t);
        return result > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("selectById id less than zero id=" + id);
        }
        int result = getSqlMapClientTemplate().delete(sqlMapNameSpacePrefix + ".delete", id);
        return result > 0 ? true : false;
    }

    @Override
    public T selectById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("selectById id less than zero id=" + id);
        }
        List<T> list = getSqlMapClientTemplate().queryForList(sqlMapNameSpacePrefix + ".selectById", id);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<T> selectAll() {
        List<T> list = getSqlMapClientTemplate().queryForList(sqlMapNameSpacePrefix+".selectAll");
        return list;
    }

    @Override
    public List<T> selectByQuery(DBBaseQueryDO queryDO) {
        if (queryDO == null) {
            throw new NullPointerException("DAO selectByQuery queryDO null");
        }
        if(queryDO.isInitPageNum()){
            queryDO.initPageNum();
        }
        if(queryDO.isInitPageSize()){
            queryDO.isInitPageSize();
        }
        queryDO.initStartNum();
        return getSqlMapClientTemplate().queryForList(sqlMapNameSpacePrefix+".selectByQuery",queryDO);
    }

    @PostConstruct
    public void initDAO() {
        if (StringUtils.isNotBlank(sqlMapNameSpacePrefix)) {
            return;
        }
        String name = getClass().getName();
        String names[] = name.split("\\.");
        int size = names.length;
        String clazzName = names[size - 1];
        int clazzNameIndex = clazzName.indexOf("DAO");
        if (clazzNameIndex > -1) {
            sqlMapNameSpacePrefix = clazzName.substring(0, clazzNameIndex);
        }
    }

    public String getSqlMapNameSpacePrefix() {
        return sqlMapNameSpacePrefix;
    }

    public void setSqlMapNameSpacePrefix(String sqlMapNameSpacePrefix) {
        this.sqlMapNameSpacePrefix = sqlMapNameSpacePrefix;
    }
}
