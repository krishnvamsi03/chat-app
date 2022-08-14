package com.chat.websocket.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        System.out.println("WebSocketDisconnectEvent.onApplicationEvent " + event.getSessionId());
    }
}
