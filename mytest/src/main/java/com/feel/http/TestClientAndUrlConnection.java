package com.feel.http;

import java.util.HashMap;

public class TestClientAndUrlConnection {
    public static void main(String[] args) {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        long start1 = System.currentTimeMillis();
        for(int i = 0;i<1000;i++){
            try {
                HttpUtils.sendGet("http://yop-console-qa.s.qima-inc.com/app/list", new HashMap<String, Object>());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end1 = System.currentTimeMillis();
        long start2 = System.currentTimeMillis();
        for (int i = 0;i<1000;i++) {
            try {               httpClientUtil.sendGet("http://yop-console-qa.s.qima-inc.com/app/list", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end2 = System.currentTimeMillis();
        System.out.println("时间1:" + (end1 - start1) + "时间2:" + (end2 - start2));
    }
}
