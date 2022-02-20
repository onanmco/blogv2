package com.cemonan.blog.lib;

import com.cemonan.blog.utils.PropertyExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Properties;

public class RedisConnector {
    private static JedisPool pool;

    private RedisConnector() {}

    private static JedisPool getPool() {
        if (pool == null) {
            String host = PropertyExtractor.getProperty("redis.host");
            int port = Integer.parseInt(PropertyExtractor.getProperty("redis.port"));
            pool = new JedisPool(host, port);
        }
        return pool;
    }

    public static Jedis getConnection() throws JedisException {
        if (pool == null) {
            RedisConnector.getPool();
        }
        return pool.getResource();
    }
}
