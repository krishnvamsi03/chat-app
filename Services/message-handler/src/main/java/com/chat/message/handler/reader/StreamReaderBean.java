package com.chat.message.handler.reader;

import com.chat.message.handler.store.mongo.MongoApi;
import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.StompClientConnection;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Configuration
@Log4j2
public class StreamReaderBean {

    private static Logger logger =
            LoggerFactory.getLogger(StreamReaderBean.class);
    @Autowired
    private StompClientConnection stompClientConnection;

    @Autowired
    private RedisStream<String> redisStream;

    @Autowired
    private MongoApi mongoApi;

    private StreamReader reader;

    @PostConstruct
    public void startStream() {
        reader = new StreamReader(redisStream,
                stompClientConnection, mongoApi);
        reader.readStream();
    }

    @Bean
    public StreamReader getReader() {
        return reader;
    }

    @Scheduled(fixedDelayString = "5000")
    public void readStream() {
        reader.readStream();
    }
}
