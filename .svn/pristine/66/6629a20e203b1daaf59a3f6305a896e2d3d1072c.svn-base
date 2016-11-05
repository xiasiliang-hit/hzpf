package com.line.bqxd.platform.manager.wxpay;

import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huangjianfei on 16/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/app*.xml"}) //加载配置文件
public class TradeManagerTest {

    @Resource  //自动注入,默认按名称
    private TradeManager tradeManager;

    @Test
    public void testTradeCreate(){
        long productId=1;
        long totalFee=1;
        //String openId="ot3tXs0AZNShis5r4mG6muF-5hEc";

        String openId="o1h54t9w83qBLyEGy-UbdT7Y5MMo";
        //BizResult<WXPayResult> result= tradeManager.tradeCreate(productId,3,totalFee,openId, BizFeeType.MEMBERFEE.getValue());
        //Assert.assertNotNull(result);

    }

    @Test
    public void testSign(){
        RequestHandler requestHandler =new RequestHandler();
        requestHandler.setParameter("appId", "wx6fae0b7142e97ce8");
        requestHandler.setParameter("nonceStr", "0S9zZkMUfUSJl1rs");
        requestHandler.setParameter("signType", "MD5");
        requestHandler.setParameter("package","prepay_id=wx20160505162425063bc10d940914292704");
        requestHandler.setParameter("timeStamp", "1462436665");
        String key="f33cf2932410625e2a7f1d1dd91a0c6c";
        String sign = PayUtil.createWXPaySign("UTF-8", requestHandler.getParameters(),key);
        System.out.println(sign);
    }
}
