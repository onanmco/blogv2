package com.cemonan.blog.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfig {

    @Bean(name = "config.FakerConfig.getFakerInstance")
    Faker getFakerInstance() {
        return new Faker();
    }
}
