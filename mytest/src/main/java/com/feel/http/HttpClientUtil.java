package com.feel.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.util.Map;


/**
 * Created by xugang on 16/7/11.
 */
public class HttpClientUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private int maxTotal = 1;//默认最大连接数
    private int defaultMaxPerRoute = 1;//默认每个主机的最大链接数
    private int connectionRequestTimeout = 3000;//默认请求超时时间
    private int connectTimeout = 3000;//默认链接超时时间
    private int socketTimeout = 3000;//默认socket超时时间
    private HttpRequestRetryHandler httpRequestRetryHandler = new DefaultHttpRequestRetryHandler();//默认不进行重试处理
    private CloseableHttpClient httpClient;
    public  HttpClientUtil(){
        init();
    }

    public  String sendGet(String url, Map<String, Object> params) throws Exception {
        StringBuffer sb = new StringBuffer(url);
        if(!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        // no matter for the last '&' character
        HttpGet httpget = new HttpGet(sb.toString());
        config(httpget);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget, HttpClientContext.create());
        } catch (IOException e) {
            logger.error("httpclient error:"+e.getMessage());
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }


    private  void init() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 设置最大连接数
        cm.setMaxTotal(maxTotal);
        // 设置每个路由的默认连接数
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);

        //连接保持时间
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                return 30 * 1000;
            }
        };

        this.httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .setKeepAliveStrategy(myStrategy)
                .build();

    }

    /**
     * http头信息的设置
     *
     * @param httpRequestBase
     */
    private void config(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        httpRequestBase.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//"en-US,en;q=0.5");
        httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        httpRequestBase.setHeader("connection", "Keep-Alive");
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * 请求重试处理
     * 默认不进行任何重试
     * 如需进行重试可参考下面进行重写
     * if (executionCount >= 5) {// 如果已经重试了5次，就放弃
     return false;
     }
     if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
     return true;
     }
     if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
     return false;
     }
     if (exception instanceof InterruptedIOException) {// 超时
     return false;
     }
     if (exception instanceof UnknownHostException) {// 目标服务器不可达
     return false;
     }
     if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
     return false;
     }
     if (exception instanceof SSLException) {// ssl握手异常
     return false;
     }

     HttpClientContext clientContext = HttpClientContext.adapt(context);
     HttpRequest request = clientContext.getRequest();
     // 如果请求是幂等的，就再次尝试
     if (!(request instanceof HttpEntityEnclosingRequest)) {
     return true;
     }
     */
    private class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler

    {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            return false;
        }
    }


}
