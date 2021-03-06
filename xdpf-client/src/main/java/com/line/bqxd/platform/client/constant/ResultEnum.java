package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/18.
 */
public enum ResultEnum {
    SUCCESS("SUCCESS","成功"),
    SYSERROR("SYSERROR","系统异常"),
    REPEAT_SUBMIT("REPEAT_SUBMIT","请勿重复提交"),

    ARGUMENT_VERIFY_FAIL("ARGUMENT_VERIFY_FAIL","参数异常"),

    USER_INSERT_FAIL("USER_INSERT_FAIL","用户失败识别"),

    CARD_LENGHT_VERIFY_FAIL("CARD_LENGHT_VERIFY_FAIL","身份证号码长度应该为18位"),
    CARD_NUMERIC_VERIFY_FAIL("CARD_NUMERIC_VERIFY_FAIL","最后一位外都应为数字"),
    CARD_BIRTH_VERIFY_FAIL("CARD_BIRTH_VERIFY_FAIL","身份证生日无效"),
    CARD_BIRTH_EFFECT_VERIFY_FAIL("CARD_BIRTH_EFFECT_VERIFY_FAIL","身份证生日不在有效范围"),
    CARD_MONTH_VERIFY_FAIL("CARD_MONTH_VERIFY_FAIL","身份证月份无效"),
    CARD_DAY_VERIFY_FAIL("CARD_DAY_VERIFY_FAIL","身份证日期无效"),
    CARD_AREACODE_VERIFY_FAIL("CARD_AREACODE_VERIFY_FAIL","身份证地区编码错误"),
    CARD_VERIFY_FAIL("CARD_VERIFY_FAIL","身份证无效，不是合法的身份证号码"),
    BIZ_CARD_ALREADY_REG_FAIL("BIZ_CARD_ALREADY_REG_FAIL","身份证号码已经被注册"),


    MOBILE_LENGHT_VERIFY_FAIL("MOBILE_LENGHT_VERIFY_FAIL","请输入11位手机号"),
    MOBILE_NUMERIC_VERIFY_FAIL("MOBILE_NUMERIC_VERIFY_FAIL","手机号码都应为数字"),
    MOBILE_VERIFY_FAIL("MOBILE_VERIFY_FAIL","手机号码验证失败"),
    MOBILE_VERIFY_SEND_FAIL("MOBILE_VERIFY_SEND_FAIL","手机验证码发送失败"),
    BIZ_MOBILE_SEND_FREQUENTLY_FAIL("BIZ_MOBILE_SEND_FREQUENTLY_FAIL","手机验证码发送太频繁"),
    BIZ_MOBILE_SEND_MINUTE_ONE_FAIL("BIZ_MOBILE_SEND_MINUTE_ONE_FAIL","手机验证码一分钟之内只能够发送一次"),
    BIZ_MOBILE_ALREADY_REG_FAIL("BIZ_MOBILE_ALREADY_REG_FAIL","手机号码已经被注册"),


    CODE_BLANK_FAIL("CODE_VERIFY_FAIL","请输入验证码"),
    CODE_VERIFY_FAIL("CODE_VERIFY_FAIL","验证码验证失败"),

    USERNAME_VERIFY_FAIL("USERNAME_VERIFY_FAIL","请输入身份证上真实姓名"),
    USERNAME_BLANK_FAIL("USERNAME_BLANK_FAIL","请输入用户真实姓名"),
    USERNAME_MAX_LENGTH_FAIL("USERNAME_MAX_LENGTH_FAIL","用户真实姓名最长10位"),

    USER_JOIN_CONCUR_AGE_FAIL("USER_JOIN_CONCUR_AGE_FAIL","用户年龄超过50岁"),
    USER_JOIN_CONCUR_DELAY_AGE_FAIL("USER_JOIN_CONCUR_DELAY_AGE_FAIL","用户等待期结束年龄超过50岁"),
    USER_JOIN_CONCUR_IP_FREQUENCY_FAIL("USER_JOIN_CONCUR_IP_FREQUENCY_FAIL","用户加入互助过于频繁,请稍等"),
    USER_JOIN_CONCUR_FAIL("USER_JOIN_CONCUR_FAIL","用户加入互助失败"),

    EXTEND_ARTICLE_NOT_EXSIT_FAIL("EXTEND_ARTICLE_NOT_EXSIT_FAIL","推广文章不存在"),


    CONCUR_NOT_EXSIT_FAIL("CONCUR_NOT_EXSIT_FAIL","互助计划不存在"),

    USER_CONCUR_DEDUCT_CASH_FAIL("USER_CONCUR_DEDUCT_CASH_FAIL","用户扣款失败"),

    PAY_FILL_CASH_INSERT_DATA_RECODE_FAIL("PAY_FILL_CASH_INSERT_DATA_RECODE_FAIL","用户充值插入数据库记录失败"),
    PAY_FILL_CASH_WX_PAY_FAIL("PAY_FILL_CASH_WX_PAY_FAIL","用户微信充值支付异常"),
    PAY_FILL_CASH_WX_COMM_FAIL("PAY_FILL_CASH_WX_COMM_FAIL","用户微信充值通讯异常"),

    PAY_FILL_CASH_FEE_FAIL("PAY_FILL_CASH_FEE_FAIL","充值金额不能为空"),


    G20_START_LOCATION_FAIL("G20_START_LOCATION_FAIL","获取早上出发地位置失败"),
    G20_START_ADDRESS_FAIL("G20_START_ADDRESS_FAIL","获取早上出发地位置异常"),
    G20_END_LOCATION_FAIL("G20_END_LOCATION_FAIL","获取早上目的地位置失败"),
    G20_END_ADDRESS_FAIL("G20_END_ADDRESS_FAIL","获取早上目的地位置异常"),


    USER_BIRTH_DAY_BANK("USER_BIRTH_DAY_BANK","出生日期不能为空"),
    USER_BIRTH_DAY_NEED_LESS_THAN_30DAY("USER_BIRTH_DAY_NEED_LESS_THAN_30DAY","出生日期必须小于等于30天"),
    USER_BIRTH_NOT_BORN("USER_BIRTH_NOT_BORN","还没有出生"),

    USER_BIRTH_WEIGHT_BANK("USER_BIRTH_WEIGHT_BANK","出生体重不能为空"),
    USER_BIRTH_WEIGHT_UNUSUAL("USER_BIRTH_WEIGHT_UNUSUAL","出生体重异常"),
    USER_BIRTH_WEIGHT_POINT_UNUSUAL("USER_BIRTH_WEIGHT_POINT_UNUSUAL","出生体重请勿输入小数点"),

    USER_BIRTH_WEEK_BANK("USER_BIRTH_WEEK_BANK","出生孕周不能为空"),



    CLAIM_VERIFY_HOSPITAL_BLANK("CLAIM_VERIFY_HOSPITAL_BLANK","请输入确诊医院"),
    CLAIM_VERIFY_TIME_BLANK("CLAIM_VERIFY_TIME_BLANK","请输入确诊时间"),
    CLAIM_VERIFY_EVENT_ID_BLANK("CLAIM_VERIFY_EVENT_ID_BLANK","请选择确诊病症"),
    CLAIM_VERIFY_USER_BLANK("CLAIM_VERIFY_USER_BLANK","请输入确诊医生"),
    CLAIM_VERIFY_TEL_BLANK("CLAIM_VERIFY_TEL_BLANK","请输入确诊医生电话"),
    CLAIM_VERIFY_TEL_FAIL("CLAIM_VERIFY_TEL_FAIL","请输入正确确诊医生电话"),

    CLAIM_VERIFY_BANK_NO_BLANK("CLAIM_VERIFY_BANK_NO_BLANK","请输入理赔银行账号"),
    CLAIM_VERIFY_BANK_NO_FAIL("CLAIM_VERIFY_BANK_NO_FAIL","请输入正确理赔银行账号"),

    CLAIM_VERIFY_BANK_NAME_BLANK("CLAIM_VERIFY_BANK_NAME_BLANK","请输入理赔银行名称"),
    CLAIM_VERIFY_BANK_NAME_FAIL("CLAIM_VERIFY_BANK_NAME_FAIL","请输入正确理赔银行名称"),

    CLAIM_DESCR_BLANK("CLAIM_DESCR_FAIL","请输入受助人的基本情况"),

    CLAIM_PIC_UPLOAD_BLANK("CLAIM_PIC_UPLOAD_BLANK","请上传资料图片"),

    CLAIM_SUBMIT_FAIL("CLAIM_SUBMIT_FAIL","申请互助失败"),


    ;

    private String code;

    private String desc;

    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "ResultEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
