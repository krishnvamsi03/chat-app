package com.chat.message.handler.store.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class RedisClientBean {

    @Value("classpath:redissentinel.yml")
    private Resource redisSentinelConfig;

    @Bean
    public RedissonClient getRedisClient() throws IOException {
        return Redisson.create(Config.fromYAML(redisSentinelConfig.getInputStream()));
    }
}
