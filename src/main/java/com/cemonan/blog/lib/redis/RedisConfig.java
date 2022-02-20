package com.cemonan.blog.lib.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Bean(name = "RedisConfig.getPool")
    public JedisPool getPool() {
        return new JedisPool(host, Integer.parseInt(port));
    }
}
