package com.line.bqxd.platform.controller.utils;

import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.manager.concur.LastUserDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/17.
 */
public class BizViewUtils {
    private final static long HOUR = 60 * 60 * 1000;
    public static List<String> lastUserFormat(LastUserDO[] lastUserDOs){
        if(lastUserDOs==null||lastUserDOs.length==0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        Date currentDate = new Date();
        for(LastUserDO lastUserDO:lastUserDOs){
            if(lastUserDO==null){
                continue;
            }
            Date joinDate=lastUserDO.getJoinDate();
            if(currentDate.getTime()-joinDate.getTime()<=HOUR){
                list.add("刚刚 "+lastUserDO.getNiceName()+"获得30万保障");
            }else if(DateUtils.isSameDate(currentDate,joinDate)){
                int diffHour=DateUtils.diffHourInSameDate(joinDate,currentDate);
                list.add(diffHour+"小时前 "+lastUserDO.getNiceName()+"获得30万保障");
            }else{
                String dataTo=DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2,currentDate);
                String dataFrom=DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2,joinDate);
                try {
                    long days = DateUtils.daysBetween(dataFrom, dataTo, DateUtils.DATA_FORMAT2);
                    list.add(days+"天前 "+lastUserDO.getNiceName()+"获得30万保障");

                }catch (Exception e){

                }
            }
        }

        return list;
    }


}
