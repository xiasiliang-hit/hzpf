package com.line.bqxd.platform.client.constant;

/**
 * Created by huangjianfei on 16/5/9.
 * 数据库中的状态集合
 */
public class Status {
    //支付状态
    public enum Pay {
        UNPAY(1)/*待付款*/,PAYSUBMIT(2)/*付款提交*/,PAYSUCCESS(3)/*付款确认成功*/,PAYFAIL(9)/*付款失败*/,REFUND(4)/*退款*/,NOTPAY(5)/*未支付*/;

        private int value = 0;

        Pay(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //微信支付状态
    public enum WXPay {
        SUCCESS/*支付成功*/,REFUND/*转入退款*/,NOTPAY/*未支付*/,CLOSED/*已关闭*/,REVOKED/*已撤销（刷卡支付）*/,USERPAYING/*用户支付中*/,PAYERROR/*支付失败*/;
    }

    //交易状态
    public enum Trade {
        NEW(1)/*新建交易*/,HANDLE(2)/*交易处理中*/,OVEN(9)/*交易结束*/;

        private int value = 0;

        Trade(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //短信验证码状态
    public enum Sms {
        NEW(1),SUCCESS(2),FAIL(3),USED(4);
        private int value=0;

        Sms(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //体现审核状态
    public enum Cash {
        NEW(1), PASS(2), REFUSH(3);
        private int value = 0;

        Cash(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    //用户申请互助保障状态
    public enum ClaimApply {

            NEW(1)/*用户提交申请*/, RECEIVE(2)/*平台受理*/, CHECK(3)/*公估公司核查*/, ACCEPT(4)/*接受理赔*/, REFUSE(5)/*拒绝理赔*/, PUBLISH(6)/*公示案件及筹款*/,COLLECTEND(7)/*筹款结束*/, BANDY(8)/*打款中*/, END(9)/*用户打款完成案件结束*/,OPPUGN(10)/*案件质疑*/,CLOSE(99)/*案件关闭*/;
        private int value = 0;

        ClaimApply(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //用户状态
    public enum UserStatus {
        NORMALLY(1)/*正常*/, DEL(2)/*删除*/, DIE(3)/*死亡*/;
        private int value = 0;

        UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum EnsureStatus {
        NEW(0)/*新增用户*/,NORMALLY(1)/*正常*/,WRAN(2)/*用户警告,一般是余额比较少*/,CLIAM(3)/*用户理赔中*/,INVALID(4)/*失效状态,一般是用户退出保障*/, CLEAN(5)/*死亡*/,DEL(6)/*用户删除*/,CLIAMREFUSE(7)/*理赔拒绝*/;
        private int value = 0;

        EnsureStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ExtendFeeStatus {
        CREATE(1)/*生成*/, BANDY(2)/*打款成功*/;
        private int value = 0;

        ExtendFeeStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
