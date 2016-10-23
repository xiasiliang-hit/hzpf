package com.line.bqxd.platform.controller.domain.request;

import com.line.bqxd.platform.client.common.Base;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huangjianfei on 16/5/16.
 */
public class UserPerfectRequest extends Base {
    private String mobile;
    private String card;
    private String userName;
    private String mcode;

    private long memberFee;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public long getMemberFee() {
        return memberFee;
    }

    public void setMemberFee(long memberFee) {
        this.memberFee = memberFee;
    }
}
