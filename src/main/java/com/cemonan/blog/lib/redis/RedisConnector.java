package com.cemonan.blog.lib.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@ComponentScan(basePackages = "com.cemonan.blog.lib.redis")
public class RedisConnector {

    private final ApplicationContext context;

    @Autowired
    public RedisConnector(ApplicationContext context) {
        this.context = context;
    }

    public Jedis getConnection() {
        RedisConfig redisConfig = context.getBean(RedisConfig.class);
        return redisConfig.getPool().getResource();
    }
}