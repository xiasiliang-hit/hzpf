package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by danny on 10/21/16.
 */

    public class UserTradeCashDO extends DBBaseDO implements Serializable {

		/*
        public UserTradeCashDO(String bankNo, String bankName, long userId, String userName
                               , String explains, long totalFee, String tradeId, String account)
        {
            this.bankNo = bankNo;
            this.bankName = bankName;
            this.userId = userId;
            this.userName = userName;

            this.explains = explains;
            this.totalFee = totalFee;
            this.tradeId = tradeId;
            this.account = account;
        }
		*/
		
    public UserTradeCashDO() {
    }
		
		@Setter
		@Getter
		private long userId;

		@Setter
		@Getter
		private String openId;

		@Setter
	    @Getter
		private String tradeId;

		@Setter
		@Getter
		private int feeToType;

		@Setter
		@Getter
		private String account;

		@Setter
		@Getter
		private String channel;

		@Setter
	    @Getter
		private long userFee;

		@Setter
		@Getter
		private long procedureFee;

		@Setter
	    @Getter
		private long totalFee;

		@Setter
		@Getter
		private int status;

		@Setter
		@Getter
		private String explains;


		@Setter
		@Getter
		private String refuseCode;

		@Setter
		@Getter
		private String refuseMsg;

		@Setter
		@Getter
		private String bankName;

		@Setter
		@Getter
		private String bankNo;

		@Setter
		@Getter
		private long withdrawAmount;
	   
    }

