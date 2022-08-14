package com.chat.websocket.events;

import com.chat.websocket.store.mongo.MongoApi;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketConnectEvent implements ApplicationListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        System.out.println("WebSocketConnectEvent.onApplicationEvent : " +
                "session id created");
    }
}
