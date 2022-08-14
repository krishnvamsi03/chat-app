package com.chat.message.handler.serviceconfig;

import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.MessageWebSocketClient;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import javax.annotation.PostConstruct;

@Configuration
public class ServiceConfigs {

    @Autowired
    private RedissonClient redisClient;
    private static String STREAM_KEY = "messages:delivery:stream";
    private static String STREAM_MAP_KEY = "messageKey";

    @Bean
    public RedisStream<String> getRedisStream() {
        RedisStream<String> redisStream =
                new RedisStream<>(redisClient.getStream(STREAM_KEY),
                        STREAM_MAP_KEY);
        return redisStream;
    }

    @Bean
    public MessageWebSocketClient getMessageClient() {
        MessageWebSocketClient messageWebSocketClient =
                new MessageWebSocketClient();
        messageWebSocketClient.createClient();
        return messageWebSocketClient;
    }

}
