package com.chat.message.handler.reader;

import com.chat.message.handler.store.mongo.MongoApi;
import com.chat.message.handler.store.redis.wrappers.RedisStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Configuration
public class StreamReaderBean {

    @Autowired
    private StompSession stompSession;

    @Autowired
    private RedisStream<String> redisStream;

    @Autowired
    private MongoApi mongoApi;

    private StreamReader reader;

    @PostConstruct
    public void startStream() {
        reader = new StreamReader(redisStream, stompSession, mongoApi);
        reader.readStream();
    }

    @Bean
    public StreamReader getReader() {
        return reader;
    }

    @Scheduled(fixedDelayString = "1000")
    public void readStream() {
        reader.readStream();
    }
}
