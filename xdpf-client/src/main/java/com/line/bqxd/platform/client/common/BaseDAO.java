package com.line.bqxd.platform.client.common;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/3.
 */
public interface BaseDAO<T> {
    /**
     * 增加记录
     * @param t
     * @return
     */
    public long insert(T t);

    /**
     * 修改记录
     * @param t
     * @return
     */
    public boolean update(T t);

    /**
     * 删除记录
     * @param id
     * @return
     */
    public boolean delete(long id);

    /**
     * 获取具体的记录
     * @param id
     * @return
     */
    public T selectById(long id);

    /**
     * 获取全部记录
     * @return
     */
    public List<T> selectAll();

    /**
     * 根据分页对象获取记录的列表
     * @param queryDO
     * @return
     */
    public List<T> selectByQuery(DBBaseQueryDO queryDO);

    public T selectByUserId(long user_id);
}
