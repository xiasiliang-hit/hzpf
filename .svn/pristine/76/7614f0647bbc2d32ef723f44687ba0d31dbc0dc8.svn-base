package com.line.bqxd.platform.common.utils;

import com.line.bqxd.platform.login.SessionUserInfo;
import org.apache.commons.lang.StringUtils;

/**
 * Created by huangjianfei on 16/6/12.
 */
public class ShowUtils {

    private final static String REPLACE_CHAR = "*";
    private final static String DEFAULT_BY_REPLACE_CHAR = "我";

    public static String replaceTitle(String text, SessionUserInfo sessionUserInfo) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        if (sessionUserInfo == null) {
            return StringUtils.replace(text, REPLACE_CHAR, "");
        }
        String byReplaceChar = sessionUserInfo.getNickName();
        if (StringUtils.isBlank(byReplaceChar)) {
            byReplaceChar = sessionUserInfo.getUserName();
        }
        byReplaceChar = StringUtils.replace(byReplaceChar, REPLACE_CHAR, "");
        if (StringUtils.isBlank(byReplaceChar)) {
            byReplaceChar = DEFAULT_BY_REPLACE_CHAR;
        }
        return StringUtils.replace(text, REPLACE_CHAR, byReplaceChar);
    }

    public static void main(String[] args){
        SessionUserInfo sessionUserInfo =new SessionUserInfo();
        //sessionUserInfo.setNickName("ceshi");
        //sessionUserInfo.setUserName("cece");
        System.out.println(ShowUtils.replaceTitle("看不*起病?来*这里免费领取30万保证金",sessionUserInfo));
    }
}
