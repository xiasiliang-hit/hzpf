package com.line.bqxd.platform.common;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by huangjianfei on 16/4/15.
 */
public class BaseDAOImpl extends SqlMapClientDaoSupport {

    @Autowired  //通过bean名称注入
    private SqlMapClient sqlMapClient;

    @PostConstruct  //完成sqlMapClient初始化工作
    public void initSqlMapClient(){
        super.setSqlMapClient(sqlMapClient);
    }
}
