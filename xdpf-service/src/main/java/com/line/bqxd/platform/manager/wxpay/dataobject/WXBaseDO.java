package com.line.bqxd.platform.manager.wxpay.dataobject;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.common.ResultEnums;
import com.line.bqxd.platform.manager.message.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by huangjianfei on 16/4/28.
 */
public class WXBaseDO extends Base implements Serializable{

    private static final long serialVersionUID = 3772343037571453193L;


    public static final String WX_SUCCESS_CODE="SUCCESS";

    public static final String WX_FAIL_CODE="FAIL";

    public WXBaseDO() {
    }

    public WXBaseDO(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public WXBaseDO(ResultEnums resultEnum) {
        this.returnCode = resultEnum.getCode();
        this.returnMsg = resultEnum.getDesc();
    }



    @XStreamAlias("return_code")
    @XStreamCDATA
    protected String returnCode;

    @XStreamAlias("return_msg")
    @XStreamCDATA
    protected String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
