package com.line.bqxd.platform.client.utils;

import org.apache.commons.lang.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by huangjianfei on 16/4/29.
 */
public class DateUtils {
    public static final String DATA_TIME_FORMAT1="yyyy-MM-dd HH:mm:ss";

    public static final String DATA_TIME_FORMAT2="yyyyMMddHHmmss";

    public static final String DATA_FORMAT1="yyyy-MM-dd";

    public static final String DATA_FORMAT2="yyyyMMdd";

    public static final String DATA_FORMAT3="yyyy年MM月dd日";

    public static final String TIME_FORMAT1="HH:mm:ss";

    public static final String TIME_FORMAT2="HHmmss";

    public static final FastDateFormat DATA_TIME_FORMAT1_INSTANCE = FastDateFormat.getInstance("yyyyMMddHHmmss");

    public static final FastDateFormat DATA_TIME_FORMAT2_INSTANCE = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static final FastDateFormat DATA_TIME_FORMAT3_INSTANCE = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");

    public static String getDateTimeFormat(long millis){
        return DATA_TIME_FORMAT1_INSTANCE.format(new Date(millis));
    }

    public static String getDateTimeFormat(Date  date){
        return DATA_TIME_FORMAT1_INSTANCE.format(date);
    }

    public static String getDateTimeFormat(){
        return DATA_TIME_FORMAT1_INSTANCE.format(new Date());
    }


    public static String getDateTimeFormat(String format,Date date){
        if(date==null){
            return null;
        }
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(format);
        return fastDateFormat.format(date);

    }


    public static String getDateTimeFormat(FastDateFormat fastDateFormat,Date date){
        return fastDateFormat.format(date);

    }

    public static Date parse(String dateString, String format) throws ParseException {

        return new SimpleDateFormat(format).parse(dateString);

    }


    public static Date Convert(String str)
    {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
        try
        {
            java.util.Date d = sdf.parse(str);
            return d;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 根据日期算年龄
     *
     * @param birthDate
     * @return
     */

    public static int countAge(Date birthDate) {
        Calendar birthday = new GregorianCalendar();

        birthday.setTime(birthDate);

        Calendar currtDate = new GregorianCalendar();

        int age = currtDate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

        //取得周岁，即如果生日还没过，那么将年龄减1

        birthday.add(Calendar.YEAR, age);

        if (birthday.after(currtDate)) {
            age--;
        }
        return age;

    }

    /**
     * 取得离date时间相差几天的日期
     * @param date 被相比的日期
     * @param days 相差的几天（正数表示向后差几天，负数表示向前差几天）
     * @return
     */

    public static Date getLeaveDay(Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(cal.DAY_OF_YEAR, days);
        return cal.getTime();

    }

    public static long daysBetween(String dateStart, String dateStop, String formatStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = null;
        Date d2 = null;
        d1 = format.parse(dateStart);
        d2 = format.parse(dateStop);

        //毫秒ms
        long diff = d2.getTime() - d1.getTime();
        return  diff / (24 * 60 * 60 * 1000);

    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static int diffHourInSameDate(Date dateFrom, Date dateTO) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateFrom);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateTO);
        int hourTo=cal2.get(Calendar.HOUR_OF_DAY);
        int hourFrom=cal1.get(Calendar.HOUR_OF_DAY);


        return hourTo-hourFrom;
    }
    public static int differDayCurrentDate(String beginDate){

        try {
            return (int) DateUtils.daysBetween(beginDate,
                    DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), DateUtils.DATA_FORMAT2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) throws Exception {
        //Date dateFrom = DateUtils.parse("20160518072323", DATA_TIME_FORMAT2);
        //System.out.println(getDateTimeFormat(DATA_TIME_FORMAT3_INSTANCE,new Date()));
        //System.out.println(diffHourInSameDate(dateFrom, new Date()));
        //int waitDays=360;
        //System.out.println(daysBetween("20160517","20160518",DATA_FORMAT2));
        //System.out.println(DateUtils.getLeaveDay(new Date(),-da));

        //Date birthDate=DateUtils.parse("19810101", "yyyyMMdd");
        //System.out.println(DateUtils.countAge(DateUtils.getLeaveDay(birthDate, -waitDays)));


        //DateUtils.parse("20161205",DATA_FORMAT2);
        //DateUtils.countAge("19960620");

        int between = (int) DateUtils.daysBetween(
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), "20160927", DateUtils.DATA_FORMAT2);
        System.out.println(between);
    }

}
