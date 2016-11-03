package com.line.bqxd.platform.controller.weixing;

import com.line.bqxd.platform.client.dataobject.UserTradeCashDO;
import com.line.bqxd.platform.dao.UserTradeCashDAO;

import javax.annotation.Resource;

/**
 * Created by danny on 10/30/16.
 */
public class Test {

    @Resource
    public static UserTradeCashDAO<UserTradeCashDO> userTradeCashDAO;

    public static int main(String[] argv)
    {
        //UserTradeCashDAO<UserTradeCashDO> userTradeCashDAO = null;
        UserTradeCashDO userTradeCashDO = new UserTradeCashDO();
        userTradeCashDO.setStatus(989898);
        userTradeCashDO.setUserId(123321);


        userTradeCashDAO.insert(userTradeCashDO);

        return 0;
    }




}
