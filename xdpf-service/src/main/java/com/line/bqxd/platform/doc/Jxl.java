package com.line.bqxd.platform.doc;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huangjianfei on 16/8/10.
 */
public class Jxl {
    private static final String BASE_URL="http://220.191.242.154/zjmgw/bsdt/pub_edit.jsp?pkid=";
    private static final int TIME_OUT=3000;



    public static WritableWorkbook createWorkbook(OutputStream os) throws IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        return workbook;

    }

    public static WritableSheet createSheet(WritableWorkbook workbook,String sheetName){
        return workbook.createSheet(sheetName, 0);
    }

    public static void writeSheet(List<String> list,WritableSheet sheet,int row)throws WriteException{
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        int index=0;
        for(String value:list){
            Label name = new Label(index, row, value);
            sheet.addCell(name);
            index++;
        }
    }


    public static void write(WritableWorkbook workbook)throws IOException {
        workbook.write();
    }

    public static void parse() throws Exception {
        File file = new File("/Users/huangjianfei/parse.xls");
        //System.out.println(file.getPath());
        FileOutputStream out = new FileOutputStream(file);
        WritableWorkbook workbook=createWorkbook(out);
        WritableSheet sheet=createSheet(workbook,"协会");
        writeSheet(getTitleList(),sheet,0);
        //write(workbook);
        int row = 1;
        int start=52172;
        int end=62172;
        while(start<=end) {
            List<String> valueList=getValueList(start);
            if(!CollectionUtils.isEmpty(valueList)) {
                writeSheet(valueList, sheet, row);
                row++;
            }
            start++;
            System.out.println(start);
        }
        write(workbook);
        workbook.close();
        out.close();



    }


    private static List<String> getTitleList(){
        List<String> list = new ArrayList<String>();
        list.add("id");
        list.add("名称");
        list.add("填报日期");
        list.add("业务类别");
        list.add("登记证号");
        list.add("状态");
        list.add("联系人");
        list.add("联系电话");
        list.add("经办人");
        list.add("经办电话");
        return list;
    }

    private static List<String> getValueList(int id) throws IOException {
        List<String> list = null;
        String urlStr=BASE_URL+id;
        URL url = new URL(urlStr);
        try {
            Document document = Jsoup.parse(url, TIME_OUT);
            Elements values = document.select("td");
            if (values != null && values.size() == 9) {
                String name = values.get(0).text();
                if (StringUtils.isNotBlank(name)) {
                    list = new ArrayList<String>();
                    list.add(String.valueOf(id));
                    list.add(name);
                    list.add(values.get(1).text());
                    list.add(values.get(2).text());
                    list.add(values.get(3).text());
                    list.add(values.get(4).text());
                    list.add(values.get(5).text());
                    list.add(values.get(6).text());
                    list.add(values.get(7).text());
                    list.add(values.get(8).text());
                }

            }

        }catch (Exception e){
            System.out.println(urlStr);
        }

        return list;


    }

    public static void main(String[] args)throws Exception{

        parse();

    }
}
