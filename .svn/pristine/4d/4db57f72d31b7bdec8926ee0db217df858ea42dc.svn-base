package com.line.bqxd.platform.doc;

import com.line.bqxd.platform.common.HttpResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
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
public class Shmzj {
    private static final String BASE_URL = "http://www.shstj.gov.cn/NGO_Search.aspx?Keywords=";

    private static final String baseUrl = "http://www.shstj.gov.cn/";
    private static final int TIME_OUT = 3000;


    public static WritableWorkbook createWorkbook(OutputStream os) throws IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        return workbook;

    }

    public static WritableSheet createSheet(WritableWorkbook workbook, String sheetName) {
        return workbook.createSheet(sheetName, 0);
    }

    public static void writeSheet(List<String> list, WritableSheet sheet, int row) throws WriteException {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int index = 0;
        for (String value : list) {
            Label name = new Label(index, row, value);
            sheet.addCell(name);
            index++;
        }
    }

    public static void writeSheet(Set<String> set, WritableSheet sheet, int row) throws WriteException {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        int index = 0;
        for (String value : set) {
            Label name = new Label(index, row, value);
            sheet.addCell(name);
            row++;
            //index++;
        }
    }

    public static void write(WritableWorkbook workbook) throws IOException {
        workbook.write();
    }

    public static void parse() throws Exception {
        File file = new File("/Users/huangjianfei/上海民政局.xls");
        //System.out.println(file.getPath());
        FileOutputStream out = new FileOutputStream(file);
        WritableWorkbook workbook = createWorkbook(out);
        WritableSheet sheet = createSheet(workbook, "协会");
        writeSheet(getTitleList(), sheet, 0);
        //write(workbook);
        int row = 1;
        int start = 1;
        int end =660;
        Map<String, String> map = new HashMap<String, String>();
        while (start <= end) {
            String content = sendList(BASE_URL, "UTF-8", map);
            if (StringUtils.isBlank(content)) {
                continue;
            }
            Document document = Jsoup.parse(content);
            Elements inputs = document.select("input");
            if (inputs != null && inputs.size() > 0) {
                map = new HashMap<String, String>();
            }
            setParasMap(inputs, map);
            Elements values = document.select(".listContn");
            Iterator<Element> iterator = values.iterator();
            try {
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    String mobileHref = element.select("a[href]").attr("href");
                    Elements td = element.select("td");
                    if (!CollectionUtils.isEmpty(td)) {
                        List<String> list = new ArrayList<String>();
                        list.add(td.get(0).text());
                        list.add(td.get(1).text());
                        list.add(td.get(2).text());
                        list.add(td.get(3).text());
                        list.add(getMobile(mobileHref));
                        writeSheet(list, sheet, row);
                        row++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            start++;
            System.out.println(start);
        }

        write(workbook);
        workbook.close();
        out.close();


    }

    private static void setParasMap(Elements posts, Map<String, String> map) {
        Iterator<Element> iterator = posts.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String name = element.attr("name");
            String value = element.attr("value");
            if ("__EVENTTARGET".equals(name)) {
                value = "ctl00$ContentPlaceHolder1$ChangePageControl1$lkNext";
            }
            //System.out.println("name="+name+",value="+value);
            map.put(name, value);
        }
    }

    private static List<String> getTitleList() {
        List<String> list = new ArrayList<String>();
        /*
        list.add("id");
        */
        list.add("社会组织名称");
        list.add("类别");
        list.add("成立日期");
        list.add("登记机关");
        list.add("联系方式");


        return list;
    }


    public static String sendList(String url, String encode, Map<String, String> map) {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(2000);
        params.setSoTimeout(2000);
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(params);
        HttpClient client = new HttpClient(connectionManager);

        HttpResult httpResult = new HttpResult();

        //GetMethod method = null;
        PostMethod method = null;
        try {
            method = new PostMethod(url);
            //method = new GetMethod(url);
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            method.setRequestHeader("Referer", "http://www.shstj.gov.cn/NGO_Search.aspx?Keywords=");
            method.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
            method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            method.setRequestHeader("Cookie", "ant_stream_56813ffed3c17=1470937527/2082742284");
            if (!CollectionUtils.isEmpty(map)) {
                Set<Map.Entry<String, String>> set = map.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    method.setParameter(entry.getKey(), entry.getValue());
                }
                //method.addParameter();
            }
            int statusCode = client.executeMethod(method);
            httpResult.setStatusCode(statusCode);
            if (statusCode != HttpStatus.SC_OK) {
            }

            byte[] responseBody = method.getResponseBody();
            String content = new String(responseBody, encode);
            httpResult.setContent(content);
            //System.out.println(content);
            //System.out.println("============================");
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

    public static String send(String url, String encode) {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(2000);
        params.setSoTimeout(2000);
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(params);
        HttpClient client = new HttpClient(connectionManager);
        GetMethod method = null;

        try {
            method = new GetMethod(url);
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            method.setRequestHeader("Referer", "http://www.shstj.gov.cn/NGO_Search.aspx?Keywords=");
            method.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
            method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            method.setRequestHeader("Cookie", "ant_stream_56813ffed3c17=1470937527/2082742284");
            //method.set
            //method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandle);
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
            }

            byte[] responseBody = method.getResponseBody();
            String content = new String(responseBody, encode);

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


    private static String getMobile(String url) throws Exception {
        url = baseUrl + url;
        String content = send(url, "UTF-8");
        String mobile = "";
        try {
            Document document = Jsoup.parse(content);
            //ctl00_ContentPlaceHolder1_lblPhone
            Elements values = document.select("span#ctl00_ContentPlaceHolder1_lblPhone");//ctl00_ContentPlaceHolder1_lblPhone
            if (values != null && !values.isEmpty()) {
                Element element = values.get(0);
                mobile = element.text();
            }
        } catch (Exception e) {

        }
        return mobile;
    }

    public static void main(String[] args) throws Exception {

        parse();

        //String r="NGO_View.aspx?OrgCode=FDD9B17BD79A09AE7B36FAE4F7A84421C32365FBA98900701347F530607E4898E3A5177E448E17BD";
        //System.out.println(getMobile(r));

    }
}
