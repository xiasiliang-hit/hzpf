package com.line.bqxd.platform.v2.auth;

/**
 * Created by huangjianfei on 16/9/1.
 */
public interface Constant {

    /**
     *获取第三方平台component_access_token
     */
    public final static String PF_ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/component/api_component_token";

    /**
     * 获取预授权码pre_auth_code
     */
    public final static String PF_PRE_AUTH_CODE_URL="https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=";

    /**
     * 使用授权码换取公众号的接口调用凭据和授权信息
     */
    public final static String PF_API_QUERY_AUTH_URL="https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=";

    /**
     * 获取（刷新）授权公众号的接口调用凭据（令牌）
     */
    public final static String PF_AUTHORIZER_REFRESH_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=";


    /**
     * 获取授权方的公众号帐号基本信息
     */
    public final static String PF_GET_AUTHORIZER_INFO_URL="https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=";

    /**
     * 平台授权链接
     */
    public final static String PF_AUTHORIZER_URL="https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=";

}
