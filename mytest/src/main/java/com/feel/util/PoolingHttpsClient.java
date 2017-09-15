package com.feel.util;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1. 除了微信支付接口，使用SimpleHttpClient，其他的httpclient请求都可以使用此类
 * 2. 简单封装了get/post的方法，支持自定义编码，自定义header
 * 3. 使用实例可参考测试用例中的代码: PoolingHttpsClientTest
 * <p>
 * Created by yuweijun on 2017-06-21.
 */
/*@Service*/
public class PoolingHttpsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoolingHttpsClient.class);

    /**
     * Per default this implementation will create no more than 2 concurrent connections per given route and no more 20 connections in total
     */
    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(new PoolingHttpClientConnectionManager())
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(3000)
                    .setConnectionRequestTimeout(3000)
                    .setSocketTimeout(15000)
                    .build())
            .build();

    private static final ThreadLocal<Map<String, String>> MAP_THREAD_LOCAL = new ThreadLocal<>();

    public PoolingHttpsClient setHeaders(Map<String, String> headers) {
        MAP_THREAD_LOCAL.set(headers);
        return this;
    }

    public String get(String url) {
        return get(url, Charsets.UTF_8);
    }

    public String get(String url, Charset charset) {
        LOGGER.info("httpclient get url {}", url);
        try {
            HttpGet httpGet = new HttpGet(url);
            return execute(httpGet, charset);
        } catch (Exception e) {
            LOGGER.error("httpclient get request error for url : {}", url, e);
        }

        return null;
    }

    public String post(String url, Map<String, String> paramsMap) {
        return post(url, Charsets.UTF_8, paramsMap);
    }

    public String post(String url, Charset charset, Map<String, String> paramsMap) {
        LOGGER.info("httpclient post url {} with params : {}", url, paramsMap);

        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, charset));
            }

            return execute(method, charset);
        } catch (Exception e) {
            LOGGER.error("httpclient post error for url : {}", url, e);
        }

        return null;
    }

    /**
     * default post as utf-8 encoding
     */
    public String post(String url, String body) {
        LOGGER.info("httpclient post url {} with body {}", url, body);
        try {
            HttpPost method = new HttpPost(url);
            if (!Strings.isNullOrEmpty(body)) {
                method.setEntity(new StringEntity(body, Charsets.UTF_8));
            }
            return execute(method, Charsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("httpclient post error for url : {}", url, e);
        }

        return null;
    }

    private String execute(HttpUriRequest request, Charset charset) {
        try {
            setHttpHeaders(request);
            CloseableHttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, charset);
                }
            } else {
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                LOGGER.error("httpclient statusCode is {} and body : {}.", statusCode, body);
            }
        } catch (Exception e) {
            LOGGER.error("httpclient execute error", e);
        } finally {
            MAP_THREAD_LOCAL.remove();
        }

        return null;
    }

    private void setHttpHeaders(HttpUriRequest request) {
        Map<String, String> map = MAP_THREAD_LOCAL.get();
        if (map != null) {
            LOGGER.info("httpclient add request headers is {}", map);
            Set<String> keys = map.keySet();
            for (String key : keys) {
                String value = map.get(key);
                LOGGER.debug("header key and value is : {} = {}", key, value);
                request.setHeader(key, value);
            }
        }
    }

    public void shutdown() throws IOException {
        httpClient.close();
    }

}
