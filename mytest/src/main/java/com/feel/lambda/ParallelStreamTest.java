package com.feel.lambda;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yulong.li on 2017/9/15.
 */
public class ParallelStreamTest {
    private static HttpClient httpClient = HttpClientBuilder.create().build();

    //设置请求和传输超时时间
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();

    private static Gson gson = new Gson();

    // 非阻塞任务
    // 实测任务快，且任务数量小时 并行反而更慢
    // 比如当数据量不大时，顺序执行往往比并行执行更快。毕竟，准备线程池和其它相关资源也是需要时间的
    public static void main(String[] args) {
        List<String> prs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            prs.add(i + "");
        }

        long start = System.currentTimeMillis();
        prs.parallelStream().forEach(pr -> {
            try {
                getPurchaseApplyInfo(pr);
            } catch (Exception e) {
                //System.out.println("exception");
                System.out.println(e);
            }
        });

        System.out.print("parallel:");
        System.out.println(System.currentTimeMillis() - start);

        long start2 = System.currentTimeMillis();
        prs.stream().forEach(pr -> {
            try {
                getPurchaseApplyInfo(pr);
            } catch (Exception e) {
                //System.out.println("exception");
                System.out.println(e);
            }
        });

        System.out.print("stream:");
        System.out.println(System.currentTimeMillis() - start2);
    }

    static void getPurchaseApplyInfo(String pr) throws Exception {
        HttpPost post = new HttpPost("http://192.168.122.217:8001/Contract/PurchaseRequest/Query");
        //HttpGet post = new HttpGet("www.baidu.com");
        post.setConfig(requestConfig);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("pr", pr));

        HttpEntity httpEntity = new UrlEncodedFormEntity(nameValuePairs);
        post.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(post);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String queryResult = EntityUtils.toString(entity, Charsets.UTF_8);
                JsonObject jsonObject = new JsonParser().parse(queryResult).getAsJsonObject();
                // 采购申请查询接口 状态0 查到，10099 pr不存在
                if (0 == jsonObject.get("errcode").getAsInt()) {

                } else if (10099 == jsonObject.get("errcode").getAsInt()) {

                } else {

                }
            }
        } else {
            System.out.println("failure");
        }
    }
}
