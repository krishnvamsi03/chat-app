package com.chat.message.handler.websocket;

import lombok.Getter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


public class MessageWebSocketClient {

    @Getter
    private StompSessionHandler sessionHandler;

    @Getter
    private WebSocketStompClient stompClient;
    private String url;

    public MessageWebSocketClient() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        this.stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        this.url = "ws://127.0.0.1:8082/pullmessage";
        this.sessionHandler = new MessageStompSessionHandler();
    }

    public void createClient() {
        stompClient.connect(url, sessionHandler);
    }
}
