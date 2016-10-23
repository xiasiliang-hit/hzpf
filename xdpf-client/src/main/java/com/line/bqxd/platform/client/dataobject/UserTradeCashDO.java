package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by danny on 10/21/16.
 */

    public class UserTradeCashDO extends DBBaseDO implements Serializable {

    @Setter
    @Getter
    private String bankNo;

    @Setter
    @Getter
    private String bankName;

    @Setter
    @Getter
    private  long userId;

    @Setter
    @Getter
    private String userName;


    @Setter
    @Getter
    private String explains;

    @Setter
    @Getter
    private long totalFee;

    @Setter
    @Getter
    private String tradeId;


    @Setter
    @Getter
    private String account;




    @Setter
    @Getter
    private String channel;


    @Setter
    @Getter
    private int status;

    @Setter
    @Getter
    private int feeToType;

    @Setter
    @Getter
    private String openId;


    @Setter
    @Getter
    private int procedureFee;


    @Setter
    @Getter
    private String refushMsg;


    @Setter
    @Getter
    private String refushCode;

    @Setter
    @Getter
    private int userFee;
    }
