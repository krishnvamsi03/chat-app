package com.chat.message.handler.reader;

import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.MessageWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Configuration
public class StreamReaderBean {

    @Autowired
    private MessageWebSocketClient messageWebSocketClient;

    @Autowired
    private RedisStream<String> redisStream;
    private StreamReader reader;

    @PostConstruct
    public void startStream() {
        reader = new StreamReader(messageWebSocketClient, redisStream);
        reader.readStream();
    }

    @Bean
    public StreamReader getReader() {
        return reader;
    }

    @Scheduled(fixedDelayString = "10000")
    public void readStream() {
        reader.readStream();
    }
}
