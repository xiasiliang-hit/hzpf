package com.line.bqxd.platform.doc;

import com.line.bqxd.platform.common.HttpResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by huangjianfei on 16/8/10.
 */
public class Bjmzj {
    private static final String BASE_URL="http://www.bjmzj.gov.cn/templet/mzj/ShowMoreArticle.jsp?class_type=mzgk&CLASS_ID=xkgs&pn=";
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

    public static void writeSheet(Set<String> set,WritableSheet sheet,int row)throws WriteException{
        if(CollectionUtils.isEmpty(set)){
            return;
        }
        int index=0;
        for(String value:set){
            Label name = new Label(index, row, value);
            sheet.addCell(name);
            row++;
            //index++;
        }
    }

    public static void write(WritableWorkbook workbook)throws IOException {
        workbook.write();
    }

    public static void parse() throws Exception {
        File file = new File("/Users/huangjianfei/北京民政局.xls");
        //System.out.println(file.getPath());
        FileOutputStream out = new FileOutputStream(file);
        WritableWorkbook workbook=createWorkbook(out);
        WritableSheet sheet=createSheet(workbook,"协会");
        writeSheet(getTitleList(),sheet,0);
        //write(workbook);
        int row = 1;
        int start=1;
        int end=75;
        Set<String> set = new HashSet<String>();
        while(start<=end) {
            getValueList(start,set);
            start++;
            System.out.println(start);
        }
        writeSheet(set,sheet,0);
        write(workbook);
        workbook.close();
        out.close();



    }


    private static List<String> getTitleList(){
        List<String> list = new ArrayList<String>();
        /*
        list.add("id");
        */
        list.add("名称");
        /*
        list.add("填报日期");
        list.add("业务类别");
        list.add("登记证号");
        list.add("状态");
        list.add("联系人");
        list.add("联系电话");
        list.add("经办人");
        list.add("经办电话");
        */
        return list;
    }

    private static void getValueList(int id,Set<String> set) throws IOException {
        String urlStr = BASE_URL + id;
       // URL url = new URL(urlStr);
        String content=send(urlStr,"UTF-8");
        try {
            Document document = Jsoup.parse(content);
            Elements values = document.select(".link_03");

            Iterator<Element> iterator=values.iterator();
            while (iterator.hasNext()){
                Element element=iterator.next();
                String value=element.text();
                if (value.startsWith("关于设立")) {
                    value = value.substring(4, value.length());
                } else if (value.startsWith("关于")) {
                    value = value.substring(2, value.length());
                }
                int index=value.indexOf("变更");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("成立");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("行政许可决定");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("许可");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("资格认定");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("增设");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("设立");
                if(index>0){
                    value=value.substring(0,index);
                }
                index=value.indexOf("注销");
                if(index>0){
                    continue;
                }
                if(!set.contains(value)){
                    set.add(value);
                }
            }

        }catch (Exception e){
            System.out.println(urlStr);
            //e.printStackTrace();
        }

    }

    public static String send(String url, String encode) {
        HttpClient client = new HttpClient();

        HttpResult httpResult = new HttpResult();
        GetMethod method = null;

        try {
            method = new GetMethod(url);
            method.setRequestHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

            //method.set
            //method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandle);
            int statusCode = client.executeMethod(method);
            httpResult.setStatusCode(statusCode);
            if (statusCode != HttpStatus.SC_OK) {
            }

            byte[] responseBody = method.getResponseBody();
            String content = new String(responseBody, encode);
            httpResult.setContent(content);
            return content;

        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

    }

    public static void main(String[] args)throws Exception{

        parse();

    }
}
