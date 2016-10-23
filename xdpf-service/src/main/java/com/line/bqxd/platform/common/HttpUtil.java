package com.line.bqxd.platform.common;

import com.alibaba.fastjson.JSONObject;
import com.line.bqxd.platform.client.common.WXBase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huangjianfei on 16/4/21.
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static JSONObject send(HttpClient client, HttpMethodRetryHandler retryHandle, String url) throws Exception {
        HttpResult result = send(client, retryHandle, url, "UTF-8");
        if (result != null && result.isSuccess()) {
            log.info("http client result {}",result);
            return JSONObject.parseObject(result.getContent());
        } else {
            log.warn("http client result fail,url={}", url);
        }

        return null;
    }


    public static WXBase send(HttpClient client, HttpMethodRetryHandler retryHandle, String url,Class<WXBase> clazz) throws Exception {
        HttpResult result = send(client, retryHandle, url, "UTF-8");
        if (result != null && result.isSuccess()) {

            return JSONObject.parseObject(result.getContent(),clazz);
        } else {
            log.warn("http client result null ,url={}", url);
        }

        return null;
    }
    /**
     * Get请求
     *
     * @param url
     * @return
     * @author huangjianfei
     */
    public static HttpResult send(HttpClient client, HttpMethodRetryHandler retryHandle, String url, String encode) {
        // HttpClient client = new HttpClient();
        HttpResult httpResult = new HttpResult();
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandle);
            int statusCode = client.executeMethod(method);
            httpResult.setStatusCode(statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("[HttpClientHelper]HttpClient execute method failed: " + method.getStatusLine());
            }

            byte[] responseBody = method.getResponseBody();
            String content = new String(responseBody, encode);
            httpResult.setContent(content);
            return httpResult;

        } catch (Throwable e) {
            log.error("[HttpClientHelper]Send  Failed ! url:" + url, e);
            e.printStackTrace();
            return httpResult;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

    }

    public static HttpResult sendBrowser(HttpClient client, HttpMethodRetryHandler retryHandle, String url, String encode) {
        // HttpClient client = new HttpClient();
        HttpResult httpResult = new HttpResult();
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandle);
            method.setRequestHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

            method.setRequestHeader("Accept-Encoding","gzip, deflate, sdch");
            method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
            method.setRequestHeader("Cache-Control","max-age=0");

            int statusCode = client.executeMethod(method);
            httpResult.setStatusCode(statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("[HttpClientHelper]HttpClient execute method failed: " + method.getStatusLine());
            }

            byte[] responseBody = method.getResponseBody();
            String content = new String(responseBody, encode);
            httpResult.setContent(content);
            return httpResult;

        } catch (Throwable e) {
            log.error("[HttpClientHelper]Send  Failed ! url:" + url, e);
            e.printStackTrace();
            return httpResult;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

    }


    public static String getPath(HttpServletRequest request){
        StringBuilder sb = new StringBuilder("http://");
        sb.append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(":");
            sb.append(request.getServerPort());
        }
        sb.append(request.getContextPath());
        sb.append(request.getServletPath());
        return sb.toString();
    }

    public static String getFullPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(getPath(request));

        if (StringUtils.isNotBlank(request.getQueryString())) {
            sb.append("?");
            sb.append(request.getQueryString());//参数
        }

        return sb.toString();

    }

    public static HttpResult sendOriginally(String requestUrl, String post, String encode) throws Exception {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
        conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
        conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
        conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
        conn.setUseCaches(false); // Post 请求不能使用缓存
        HttpResult httpResult = new HttpResult();
        // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
        conn.setRequestProperty("Content-Length", requestUrl.length() + "");
        try {
            writeBodyByOutputStream(conn.getOutputStream(), encode, post);
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                httpResult.setStatusCode(conn.getResponseCode());
                return httpResult;
            }
            // 获取响应内容体
            String body = readBodyByInputStream(conn.getInputStream(), encode);
            httpResult.setStatusCode(HttpURLConnection.HTTP_OK);
            httpResult.setContent(body);
        } catch (Exception e) {
            log.error("http process error", e);
        } finally {
            conn.disconnect();
        }

        return httpResult;
    }

    public static JSONObject conver(HttpResult result) throws Exception {
        if (result != null && result.isSuccess()) {
            log.info("http client result {}",result);
            return JSONObject.parseObject(result.getContent());
        } else {
            log.warn("http client result fail,result={}", result);
        }
        return null;
    }


    public static void writeBodyByOutputStream(OutputStream outputStream, String encode, String post) throws IOException {
        if (outputStream == null) {
            throw new NullPointerException("outputStream null");
        }
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(outputStream, encode);
            out.write(post);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String readBodyByInputStream(InputStream inputStream, String encode) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream null");
        }
        StringBuffer strBuf = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream, encode));
            String line = "";
            while ((line = in.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
        } finally {
            if (in != null) {
                in.close();
            }

        }
        return strBuf.toString();
    }
}
