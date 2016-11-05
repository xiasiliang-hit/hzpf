package com.line.bqxd.platform.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/6/3.
 */
public class LinkTool {
    public static final String HTML_QUERY_DELIMITER = "&";

    private String originalUrl = null;

    private String path;

    protected Map query;

    private String fragment;

    private String scheme;

    private String host;

    private int port;

    private static final String ACTIVITY_KEY_NAME="activityId";

    private static final String CHANNEL_KEY_NAME="channelId";

    private static final String SHARE_TEXT_KEY_NAME="shareTextId";

    public static final String DEFAULT_CHARSET = "UTF-8";


    public LinkTool(String url) {
        this.originalUrl = url;
        URI uri = toURI(url);
        query = parseQuery(uri.getQuery(), HTML_QUERY_DELIMITER);
        path = uri.getPath();
        if (path.equals("/") || path.length() == 0) {
            path = null;
        }
        path = uri.getPath();
        fragment = uri.getFragment();
        scheme = uri.getScheme();
        host = uri.getHost();
        port = uri.getPort();
        ;
    }

    protected URI toURI(Object obj) {
        if (obj instanceof URI) {
            return (URI) obj;
        } else {
            try {
                return new URI(String.valueOf(obj));
            } catch (Exception e) {
                return null;
            }
        }
    }


    protected Map<String, Object> parseQuery(String query, String queryDelim) {
        if(StringUtils.isBlank(query)){
            return null;
        }
        if (query.startsWith("?")) {
            query = query.substring(1, query.length());
        }
        String[] pairs = query.split(queryDelim);
        if (pairs.length == 0) {
            return null;
        }
        Map<String, Object> params = new LinkedHashMap<String, Object>(pairs.length);
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            String key = kv[0];
            Object value = kv.length > 1 ? kv[1] : null;
            if (params.containsKey(kv[0])) {
                Object oldval = params.get(key);
                if (oldval instanceof List) {
                    ((List) oldval).add((String) value);
                    value = oldval;
                } else {
                    List<String> list = new ArrayList<String>();
                    list.add((String) oldval);
                    list.add((String) value);
                    value = list;
                }
            }
            params.put(key, value);
        }
        return params;
    }

    public void setParamIfAbsent(Object key, Object value) {
        key = String.valueOf(key);
        if (this.query == null) {
            this.query = new LinkedHashMap();
            putParam(key, value);
        } else if (!query.containsKey(key)) {
            putParam(key, value);
        }
    }

    public void removeParam(Object key){
        if(query!=null) {
            query.remove(key);
        }
    }

    public void setParam(Object key, Object value, boolean append) {
        // use all keys as strings, even null -> "null"
        key = String.valueOf(key);
        if (this.query == null) {
            this.query = new LinkedHashMap();
            putParam(key, value);
        } else if (append) {
            appendParam((String) key, value);
        } else {
            putParam(key, value);
        }
    }

    private void appendParam(String key, Object value) {
        if (query.containsKey(key)) {
            Object cur = query.get(key);
            if (cur instanceof List) {
                addToList((List) cur, value);
            } else {
                List vals = new ArrayList();
                vals.add(cur);
                addToList(vals, value);
                putParam(key, vals);
            }
        } else {
            putParam(key, value);
        }
    }

    private void putParam(Object key, Object value) {
        if (value instanceof Object[]) {
            List vals = new ArrayList();
            for (Object v : ((Object[]) value)) {
                vals.add(v);
            }
            value = vals;
        }
        query.put(key, value);
    }

    private void addToList(List vals, Object value) {
        if (value instanceof List) {
            for (Object v : ((List) value)) {
                vals.add(v);
            }
        } else if (value instanceof Object[]) {
            for (Object v : ((Object[]) value)) {
                vals.add(v);
            }
        } else {
            vals.add(value);
        }
    }


    /**
     * Converts the map of keys to values into a query string.
     */
    public String toQuery(Map parameters) {
        if (parameters == null) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        //对一些特殊参数做了特殊处理
        if(parameters.containsKey(ACTIVITY_KEY_NAME)){
            if (query.length() > 0) {
                query.append(HTML_QUERY_DELIMITER);
            }
            query.append(toQuery(ACTIVITY_KEY_NAME, parameters.get(ACTIVITY_KEY_NAME)));
        }
        if(parameters.containsKey(CHANNEL_KEY_NAME)){
            if (query.length() > 0) {
                query.append(HTML_QUERY_DELIMITER);
            }
            query.append(toQuery(CHANNEL_KEY_NAME, parameters.get(CHANNEL_KEY_NAME)));
        }
        if(parameters.containsKey(SHARE_TEXT_KEY_NAME)){
            if (query.length() > 0) {
                query.append(HTML_QUERY_DELIMITER);
            }
            query.append(toQuery(SHARE_TEXT_KEY_NAME, parameters.get(SHARE_TEXT_KEY_NAME)));
        }
        for (Object e : parameters.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
            //add new pair to this LinkTool's query data
            String key=String.valueOf(entry.getKey());
            if(ACTIVITY_KEY_NAME.equals(key)||CHANNEL_KEY_NAME.equals(key)||SHARE_TEXT_KEY_NAME.equals(key)){
                continue;
            }
            if (query.length() > 0) {
                query.append(HTML_QUERY_DELIMITER);
            }
            query.append(toQuery(entry.getKey(), entry.getValue()));
        }
        return query.toString();
    }

    protected String toQuery(Object key, Object value) {
        StringBuilder out = new StringBuilder();
        if (value == null) {
            out.append(key);
            out.append('=');
            /* Interpret null as "no value" */
        } else if (value instanceof List) {
            appendAsArray(out, key, ((List) value).toArray());
        } else if (value instanceof Object[]) {
            appendAsArray(out, key, (Object[]) value);
        } else {
            out.append(key);
            out.append('=');
            out.append(encode(value));
        }
        return out.toString();
    }


    protected void appendAsArray(StringBuilder out, Object key, Object[] arr) {
        String encKey = encode(key);
        for (int i = 0; i < arr.length; i++) {
            out.append(encKey);
            out.append('=');
            if (arr[i] != null) {
                out.append(encode(arr[i]));
            }
            if (i + 1 < arr.length) {
                out.append(HTML_QUERY_DELIMITER);
            }
        }
    }

    public String encode(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return URLEncoder.encode(String.valueOf(obj), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    /**
     * Delegates decoding of the specified url content to
     * {@link URLDecoder#decode} using the configured character encoding.
     *
     * @return String - the decoded url.
     */
    public String decode(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return URLDecoder.decode(String.valueOf(obj), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }


    protected URI createURI() {
        try {
            // fail if there was an error in setting the port
            if (port > -2) {
                // only create the URI if we have some values besides a port
                if (scheme == null && host == null
                        && path == null && query == null && fragment == null) {
                    return null;
                }
                return new URI(scheme, null, host, port, path, toQuery(query), fragment);

            }
        } catch (Exception e) {
        }
        return null;
    }

    public String toString() {
        URI uri = createURI();
        if (uri == null) {
            return null;
        }
        /*
        if (query != null)
        {
            return decodeQueryPercents(uri.toString());
        }
        */
        return uri.toString();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getPath() {
        return path;
    }

    public Map getQuery() {
        return query;
    }

    public String getFragment() {
        return fragment;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }


    public LinkTool toCopyLinkTool(){
        LinkTool linkTool = new LinkTool(this.toString());
        return linkTool;
    }

    public static void main(String[] args) {
        String url = "http://www.xiongdihuzhu.com/cond/index.htm?dd=332&rr=123&a=123#123";
        LinkTool linkTool = new LinkTool(url);
        linkTool.appendParam("activityId","1");
        linkTool.appendParam("channelId","2");
        linkTool.appendParam("shareTextId","3");
        linkTool.appendParam("a","1");

        URI uri = linkTool.toURI(url);
        System.out.println(linkTool.toString());
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());
        System.out.println(uri.getRawPath());
    }
}
