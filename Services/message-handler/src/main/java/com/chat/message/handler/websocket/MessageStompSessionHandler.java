package com.chat.message.handler.websocket;

import com.chat.message.handler.serviceconfig.ServiceConfigs;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

import java.util.Collections;


@Log4j2
public class MessageStompSessionHandler extends StompSessionHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(MessageStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session,
                               StompHeaders connectedHeaders) {
        logger.info("connected successfully session id {}",
                session.getSessionId());
    }
}
