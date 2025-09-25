package com.wzh.springai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
class RedisConfig {

    @Bean
    LettuceConnectionFactory connectionFactory() {
        // 获取 Redis 配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("127.0.0.1");
        redisConfig.setPort(6379);
        redisConfig.setDatabase(0);
        // Note: Remove username/password if Redis is not configured with authentication
        // redisConfig.setUsername("default");  // 设置 Redis 用户名
        // redisConfig.setPassword("123456");  // 设置 Redis 密码

        // 使用配置创建连接工厂
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}