package com.chat.message.handler.serviceconfig;

import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.StompClientConnection;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@Configuration
@Log4j2
public class ServiceConfigs {

    Logger logger = LoggerFactory.getLogger(ServiceConfigs.class);
    @Autowired
    private RedissonClient redisClient;

    private static String STREAM_KEY = "messages:delivery:stream";
    private static String STREAM_MAP_KEY = "messageKey";

    private StompClientConnection clientConnection;

    @PostConstruct
    public void initializeClient() throws ExecutionException,
            InterruptedException, UnknownHostException {
        clientConnection = new StompClientConnection();
        clientConnection.setupClientConnection();
    }

    @Bean
    public StompClientConnection getClientConnection() {
        return clientConnection;
    }

    @Bean
    public RedisStream<String> getRedisStream() {
        RedisStream<String> redisStream =
                new RedisStream<>(redisClient.getStream(STREAM_KEY),
                        STREAM_MAP_KEY);
        return redisStream;
    }

}
