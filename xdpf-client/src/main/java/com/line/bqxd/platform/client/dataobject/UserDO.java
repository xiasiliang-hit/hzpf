package com.line.bqxd.platform.client.dataobject;

import com.line.bqxd.platform.client.common.DBBaseDO;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by huangjianfei on 16/4/26.
 */
public class UserDO extends DBBaseDO{

    private static final long serialVersionUID = 1348989944780225416L;

    private long userId;

    private String openid;

    private String unionid;

    private String nickName;

    private String userName;

    private String password;

    private String mobile;

    private String card;

    private String bankNo;

    private String bankName;

    private int sex;

    private String birth;

    private String province;

    private String city;

    private String country;

    private String headImgurl;

    private int status;

    private int level;

    private long inviteUserId;

    private Date lastLoginDate;

    private String lastLoginIp;

    private long channelId;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public long getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(long inviteUserId) {
        this.inviteUserId = inviteUserId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @param wxUserDO
     * @return
     */
    public boolean compareToWXData(UserDO wxUserDO) {
        if (!StringUtils.equals(this.getHeadImgurl(), (wxUserDO.getHeadImgurl()))
                || this.getSex() != wxUserDO.getSex()
                || !StringUtils.equals(this.getCity(), (wxUserDO.getCity()))
                || !StringUtils.equals(this.getProvince(), (wxUserDO.getProvince()))
                || !StringUtils.equals(this.getNickName(), (wxUserDO.getNickName()))
                || !StringUtils.equals(this.getCountry(), (wxUserDO.getCountry()))) {
            return false;
        }
        return true;
    }

    public boolean checkUserMobile(UserDO wxUserDO)
    {
        if (this.getMobile() ==  null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
