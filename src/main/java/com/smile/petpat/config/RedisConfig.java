package com.smile.petpat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    private final String redisHost;
    private final int redisPort;
    private final String password;

    public RedisConfig(@Value("${spring.redis.host}") final String redisHost,
                       @Value("${spring.redis.port}") final int redisPort,
                       @Value("${spring.redis.password}") final String password) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.password  = password;
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(redisHost);
        redisConfiguration.setPort(redisPort);
        redisConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisConfiguration);
    }


    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
