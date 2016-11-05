package com.line.bqxd.platform.dao.impl;

import com.line.bqxd.platform.client.dataobject.SmsDO;
import com.line.bqxd.platform.dao.SimpleDAOImpl;
import com.line.bqxd.platform.dao.SmsDAO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangjianfei on 16/5/6.
 */
public class SmsDAOImpl extends SimpleDAOImpl implements SmsDAO<SmsDO> {

    @Override
    public SmsDO getSmsByRecNumAndStatus(String recNum, int status) {
        SmsDO smsDO = new SmsDO();
        smsDO.setStatus(status);
        smsDO.setRecNum(recNum);
        smsDO.setBizType("reg");
        List<SmsDO> list = getSqlMapClientTemplate().queryForList(getSqlMapNameSpacePrefix() + ".getSmsByRecNumAndStatus", smsDO);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<SmsDO> getSmsByRecNum(String recNum) {
        return getSqlMapClientTemplate().queryForList(getSqlMapNameSpacePrefix() + ".getSmsByRecNum", recNum);
    }
}
