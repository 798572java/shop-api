package com.fh.shop.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private RedisPool(){}

    private static JedisPool jedisPool;

    private static void jedispool(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
         jedisPool = new JedisPool(jedisPoolConfig,"192.168.197.130",7020);
    }

    static{
        jedispool();
    }

    public static Jedis getResource(){
        return jedisPool.getResource();
    }

}
