package com.feel.util;

/*import org.junit.Before;
import org.junit.Test;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuweijun on 2017-06-22.
 *//*
public class HttpsClientServiceTest {

    private static String UNI_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private static String REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
    private static String QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    HttpsClientService httpsClientService;

    String data = "<xml>test</xml>";

    @Before
    public void before() {
        httpsClientService = new HttpsClientService();
    }

    @Test
    public void test1() {
        // NoHttpResponseException : api.mch.weixin.qq.com:443 failed to respond
        Map<String, String> params = new HashMap<>();
        params.put("test", "a");
        String body = httpsClientService.post(PAY_REFUND, params);
        logger.info(body);
    }

    @Test
    public void test2() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "a");
        String body = httpsClientService.post(UNI_ORDER_URL, params);
        logger.info(body);
    }

    @Test
    public void test3() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "a");
        String body = httpsClientService.post(QUERY_URL, params);
        logger.info(body);
    }

    @Test
    public void test4() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "en-US,en;q=0.8,zh");
        headers.put("Connection", "keep-alive");
        headers.put("Lyancafe", "Coffee-Box");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");

        Map<String, String> params = new HashMap<>();
        params.put("test", "a");
        String body = httpsClientService.setHeaders(headers).post(REFUND_QUERY, params);
        logger.info(body);
    }

    @Test
    public void test7() {
        String url = "https://httpbin.org/get";
        String body = httpsClientService.get(url);
        logger.info(body);
    }

    @Test
    public void jd() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "en-US,en;q=0.8,zh");
        headers.put("Connection", "keep-alive");
        headers.put("Lyancafe", "Coffee-Box");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");

        String url = "https://www.jd.com/";
        String body = httpsClientService.setHeaders(headers).get(url);
        logger.info(body);
    }

    @Test
    public void sogou() {
        String url = "https://www.sogou.com/";
        String body = httpsClientService.get(url);
        logger.info(body);
    }

    @Test
    public void baidu() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "en-US,en;q=0.8,zh");
        headers.put("Connection", "keep-alive");
        headers.put("Lyancafe", "Coffee-Box");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");

        String url = "https://www.baidu.com/";
        String body = httpsClientService.setHeaders(headers).get(url);
        logger.info(body);
    }

    @Test
    public void so() {
        String url = "https://www.so.com/";
        String body = httpsClientService.get(url);
        logger.info(body);
    }

    @Test
    public void weibo() {
        String url = "https://weibo.com/";

        String body = httpsClientService.get(url, Charset.forName("GBK"));
        logger.info(body);
    }

    @Test
    public void headers() {
        String url = "https://httpbin.org/anything?test=1";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "en-US,en;q=0.8,zh");
        headers.put("Connection", "keep-alive");
        headers.put("Lyancafe", "Coffee-Box");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");

        String body = httpsClientService.setHeaders(headers).get(url);
        logger.info(body);
    }

}*/