package com.chat.websocket.serviceconfig;

import com.chat.websocket.store.mongo.MongoApi;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MongoApi mongoApi;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/pullmessage")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message,
                                      MessageChannel channel) {
                StompHeaderAccessor stompHeaderAccessor =
                        MessageHeaderAccessor.getAccessor(message,
                                StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
                    String username =
                            stompHeaderAccessor.getNativeHeader("username").get(0);
                    stompHeaderAccessor.setUser(new UserPrincipal(username));
                    stompHeaderAccessor.setLeaveMutable(true);
                }
                return message;
            }
        });
    }
}
