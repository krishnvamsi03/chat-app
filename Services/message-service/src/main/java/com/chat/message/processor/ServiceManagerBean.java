package com.chat.message.processor;

import com.chat.message.Builder.MessageBodyBuilder;
import com.chat.message.store.redis.wrappers.RedisStream;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceManagerBean {

    @Autowired
    private RedissonClient redisClient;

    private static String STREAM_KEY = "messages:delivery:stream";
    private static String STREAM_MAP_KEY = "messageKey";
    private static String GROUP_NAME = "MessagesGroup";

    @Bean
    public ServiceManager getServiceManager() {
        return new ServiceManager(new MessageBodyBuilder());
    }

    @Bean
    public PublishMessage getPublishMessage() {
        return new PublishMessage(initializeStream());
    }

    private RedisStream<String> initializeStream() {
        RedisStream<String> redisStream =
                new RedisStream<>(redisClient.getStream(STREAM_KEY),
                        STREAM_MAP_KEY);
        redisStream.createGroup(GROUP_NAME);
        return redisStream;
    }
}
