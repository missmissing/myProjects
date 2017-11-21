package com.feel.redis;

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        //Jedis jedis = new Jedis("192.168.213.128");
        Jedis jedis = new Jedis("sen201.dev.rs.com",26380);
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());

    }
}
