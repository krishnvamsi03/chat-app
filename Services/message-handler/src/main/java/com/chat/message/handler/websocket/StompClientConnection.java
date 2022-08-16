package com.chat.message.handler.websocket;

import lombok.Getter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StompClientConnection {

    @Getter
    private StompSession stompSession;

    @Getter
    private StompSessionHandler sessionHandler;

    @Getter
    private WebSocketStompClient stompClient;

    private String WEBSOCKET_SERVER_URI = "ws://localhost:8082/pullmessage";
    private static String USERID = "username";

    public StompClientConnection() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockjsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockjsClient);
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        sessionHandler = new MessageStompSessionHandler(this);
    }

    public void setupClientConnection() throws UnknownHostException,
            ExecutionException, InterruptedException {
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.put(USERID,
                Collections.singletonList(InetAddress.getLocalHost().getHostName()));

        this.stompClient.connect(WEBSOCKET_SERVER_URI,
                new WebSocketHttpHeaders(),
                stompHeaders,
                this.sessionHandler).addCallback(new SuccessCallback<StompSession>() {
            @Override
            public void onSuccess(StompSession result) {
                stompSession = result;
                stompSession.setAutoReceipt(true);
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                try {
                    Thread.sleep(8000);
                    setupClientConnection();
                } catch (Exception e) {

                }
            }
        });
    }
}
