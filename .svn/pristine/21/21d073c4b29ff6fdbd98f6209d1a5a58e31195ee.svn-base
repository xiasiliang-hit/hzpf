package com.line.bqxd.platform.client.utils;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.BizTradeType;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.dataobject.UserConcurRelationDO;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by huangjianfei on 16/5/9.
 */
public class BizUtils {
    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    public static String getBirth(String card) {
        if (StringUtil.isNotBlank(card)) {
            if (card.length() == 18) {
                return card.substring(6, 14);
            }
            if (card.length() == 15) {
                return "19" + card.substring(6, 12);
            }

        }
        return null;

    }

    public static int getSex(String card){
        if (StringUtil.isNotBlank(card)) {
            if (card.length() == 18) {
                int sexIndex = Integer.parseInt(card.substring(16, 17));
                return sexIndex % 2 == 0 ? 2 : 1;
            }
            if (card.length() == 15) {
                int sexIndex = Integer.parseInt(card.substring(14, 15));
                return sexIndex % 2 == 0 ? 2 : 1;
            }

        }
        return 0;
    }
    //TODO 是否需要到纳秒,这样肯定保证唯一,毫秒还是存在一定相同的可能
    public static synchronized String getTradeIdV1(String prefix,BizTradeType bizTradeType){
        StringBuilder sb= new StringBuilder();
        sb.append("V1");
        sb.append(prefix);

        //Date currentDate=new Date();
        sb.append(DateUtils.getDateTimeFormat(DateUtils.DATA_TIME_FORMAT3_INSTANCE,new Date()));
        //sb.append(bizTradeType.getValue());
        //sb.append(System.nanoTime());
        return sb.toString();
    }


    /**
     * 根据出身日期确定分摊系统
     * @param birth
     * @return
     * @throws Exception
     */
    public static float getRatioByAgeV1(String birth) throws Exception {
        if (StringUtils.isBlank(birth)) {
            throw new NullPointerException("birth is null");
        }
        int age = DateUtils.countAge(DateUtils.parse(birth, "yyyyMMdd"));
        if (age > 0 && age <= 18) {
            return 0.5f;
        } else if (age >= 18 && age <= 35) {
            return 1f;
        } else if (age >= 36 && age <= 50) {
            return 2f;
        } else if (age >= 51 && age <= 80) {
            return 0f;
        } else {
            return 0f;
        }

    }

    /**
     * 获取分摊费用,分单位
     * @param age
     * @return
     * @throws Exception
     */
    public static long getMaxRatioMoney(int age) {
        if (age > 0 && age <= 18) {
            return 150;
        } else if (age >= 18 && age <= 35) {
            return 300;
        } else if (age >= 36 && age <= 50) {
            return 600;
        } else {
            return 0;
        }
    }

    /**
     * 根据出身日期获取等待天数
     * @param birth
     * @return
     * @throws Exception
     */
    public static int getWaitDaysByAgeV1(String birth) throws Exception {
        if (StringUtils.isBlank(birth)) {
            throw new NullPointerException("birth is null");
        }
        int age = DateUtils.countAge(DateUtils.parse(birth, "yyyyMMdd"));
        if (age > 0 && age <= 40) {
            return 180;
        } else {
            return 360;
        }

    }

    /**
     * 根据出身日期获取等待天数
     * @param birth
     * @return
     * @throws Exception
     */
    public static int getWaitDaysByAgeV2(String birth) throws Exception {
        return 180;

    }

    public static boolean checkBirth(String birth, String current) throws Exception {
        long d = DateUtils.daysBetween(birth, current, DateUtils.DATA_FORMAT2);
        return d > 30 ? false : true;
    }

    public static String getEnsureStartTime(String birth, String current) throws Exception {

        long d = DateUtils.daysBetween(birth, current, DateUtils.DATA_FORMAT2);
        if (d >= 0 && d <= 10) {
            return current;
        } else if (d > 10 && d <= 30) {
            SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATA_FORMAT2);
            Date d1 = null;
            d1 = format.parse(current);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.add(Calendar.DAY_OF_YEAR,15);
            Date rtd = calendar.getTime();
            return DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, rtd);

        } else {
            throw new IllegalArgumentException("birth=" + birth + " current=" + current + " error");
        }

    }

    public static int getRatio(long weight, int childWeek) {
        int weightRatio = 0;
        int childWeekRario = 0;
        if (weight <= 1000) {
            weightRatio = 9;
        } else if (weight > 1000 && weight <= 1500) {
            weightRatio = 5;
        } else {
            weightRatio = 1;
        }
        if (childWeek <= 29) {
            childWeekRario = 9;
        } else if (childWeek > 29 && childWeek <= 31) {
            childWeekRario = 5;
        } else {
            childWeekRario = 1;
        }
        return weightRatio >= childWeekRario ? weightRatio : childWeekRario;
    }

    public static String getStartEnsureDate(String birth,Date date) throws Exception {
        int waitDays=getWaitDaysByAgeV2(birth);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,waitDays);
        return DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2,calendar.getTime());
    }


    public static String generatorRandom(int lenght){
        StringBuilder sb= new StringBuilder(lenght);
        Random ra =new Random();
        for (int i=0;i<lenght;i++){
            sb.append(ra.nextInt(10));
        }

        return sb.toString();
    }

    /*快速获取赔付费用,简单只是一个人出险*/
    public static long fastAatioMoneyFen(long allRatio,long ensureLimit, long  userRatio,int age) {

        long calculateRatioMoney = ensureLimit * 100 * userRatio / allRatio;
        long maxRatioMoney = getMaxRatioMoney(age);
        return calculateRatioMoney > maxRatioMoney ? maxRatioMoney : calculateRatioMoney;
    }

    public static String fastAatioMoneyYuan(long allRatio, long ensureLimit, long userRatio, int age) {
        long fen = fastAatioMoneyFen(allRatio, ensureLimit, userRatio, age);
        return BigDecimal.valueOf(Long.valueOf(fen)).divide(new BigDecimal(100)).toString();

    }


    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Long amount) throws Exception{
        /*
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        */
        if (amount == null || amount.longValue() == 0) {
            return "0";
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(Long amount){
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
    }

    public static long finallyEnsure(long ensureLimit, int aleadyWaitDays, UserConcurRelationDO userConcurRelationDO) {
        if (userConcurRelationDO == null) {
            throw new NullPointerException("userConcurRelationDO null");
        }
        long finallyEnsure = ensureLimit;
        /*
        int preDayAmount = userConcurRelationDO.getPreDayAmount();
        long inviteAmount = userConcurRelationDO.getInviteAmount();
        long wxAttentionAmount = userConcurRelationDO.getWxAttentionAmount();

        long preInviteAmount = userConcurRelationDO.getPreInviteAmount();
        //long inviteCount = userConcurRelationDO.getInviteCount();


        finallyEnsure += aleadyWaitDays * preDayAmount;
        int count = userConcurRelationDO.getInviteCount() > userConcurRelationDO.getInviteMaxCount() ? userConcurRelationDO.getInviteMaxCount() : userConcurRelationDO.getInviteCount();
        finallyEnsure += count* preInviteAmount;
        finallyEnsure += inviteAmount;
        finallyEnsure += wxAttentionAmount;
        */
        return finallyEnsure;
    }


    public static long getWanYuan(long amount){
        return amount/10000;
    }

    public static long getWanYuan(String amount){
        return Long.parseLong(amount)/10000;
    }

    public static String escapeMysql(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        StringBuilder sb = new StringBuilder();
        char[] array=str.toCharArray();
        for(char c:array){
            if(c=='\\'||c=='\''||c=='\"'||c=='%'||c=='_'){
                sb.append('\\');
            }
            sb.append(c);
        }
       return sb.toString();
    }
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        }else{
            return source;
        }
    }

    public static String formatDistance(double distance){
        if(distance<=0.1){
            return "0.1";
        }
        //double kd=BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(1000)).doubleValue();

        BigDecimal   b   =   new   BigDecimal(distance);
        double   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }
    public static String changeG2KG(long amount){
        return BigDecimal.valueOf(amount).divide(new BigDecimal(1000)).toString();

    }
    public static void main(String[] args) throws Exception{

        System.out.println(changeF2Y(new Long(0)));
    }


}
