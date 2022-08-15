package com.chat.message.handler.serviceconfig;

import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.MessageStompSessionHandler;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
@Log4j2
public class ServiceConfigs {

    Logger logger = LoggerFactory.getLogger(ServiceConfigs.class);
    @Autowired
    private RedissonClient redisClient;

    private static String STREAM_KEY = "messages:delivery:stream";
    private static String STREAM_MAP_KEY = "messageKey";

    private String WEBSOCKET_SERVER_URI = "ws://localhost:8082/pullmessage";

    private StompSession stompSession;

    private StompSessionHandler sessionHandler;
    private WebSocketStompClient stompClient;

    private static String USERID = "username";

    @PostConstruct
    public void initializeClient() throws ExecutionException,
            InterruptedException, UnknownHostException {
        logger.info("Initiating client side connection");
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockjsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockjsClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new MessageStompSessionHandler();
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.put(USERID,
                Collections.singletonList(InetAddress.getLocalHost().getHostName()));
        stompSession = this.stompClient.connect(WEBSOCKET_SERVER_URI,
                new WebSocketHttpHeaders(),
                stompHeaders,
                this.sessionHandler).get();
        logger.info("Client connected successfully");

    }

    @Bean
    public RedisStream<String> getRedisStream() {
        RedisStream<String> redisStream =
                new RedisStream<>(redisClient.getStream(STREAM_KEY),
                        STREAM_MAP_KEY);
        return redisStream;
    }

    @Bean
    public StompSession getStompSession() {
        return stompSession;
    }

}
