package com.cemonan.blog.lib;

import com.cemonan.blog.utils.PropertyExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Properties;

@Configuration
public class RedisConnector {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Bean
    public JedisPool getPool() {
        return new JedisPool(host, Integer.parseInt(port));
    }

    public Jedis getConnection() throws JedisException {
        return getPool().getResource();
    }
}
