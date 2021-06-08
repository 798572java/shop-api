package com.fh.shop.util;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisUtil {


    public static void hmset(String key, Map<String,String> map){
        Jedis jedis=null;
        try {
            jedis = RedisPool.getResource();
            jedis.hmset(key,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        } finally {
            if(null !=jedis ){
                jedis.close();
            }
        }
    }

    public static String hget(String key,String field){
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            return resource.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (resource != null){
                resource.close();
            }
        }
    }




    public static void  set (String key,String value){
        Jedis jedis=null;
        try {
             jedis = RedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        } finally {
            if(null !=jedis ){
                jedis.close();
            }
        }
    }

    public static String get(String key){
        Jedis jedis=null;
        try {
             jedis = RedisPool.getResource();
            String s = jedis.get(key);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(null !=jedis ){
                jedis.close();
            }
        }
    }
    //删除
    public static Long del(String key){
        Jedis jedis=null;
        try {
             jedis = RedisPool.getResource();
             return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static void setEx(String key,String value,int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.setex(key,seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }


    public static void er(String key,int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }


    public static Boolean ex(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();

            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }


}
