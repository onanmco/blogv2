package com.cemonan.blog.lib;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisConnector {
    private static JedisPool pool;

    @Value("redis.host")
    private static String host;

    @Value("redis.port")
    private static String port;

    private RedisConnector() {}

    private static JedisPool getPool() {
        if (pool == null) {
            pool = new JedisPool(host, Integer.parseInt(port));
        }
        return pool;
    }

    public static Jedis getConnection() throws JedisException {
        return pool.getResource();
    }
}
