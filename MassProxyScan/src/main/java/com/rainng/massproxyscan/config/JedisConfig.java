package com.rainng.massproxyscan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * Jedis自动配置
 */
@Configuration
public class JedisConfig {
    /**
     * Redis主机地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * Redis主机端口
     */
    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public JedisPool jedisPoolFactory() {
        return new JedisPool(host, port);
    }
}
