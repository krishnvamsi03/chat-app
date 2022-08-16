package com.chat.message.handler.websocket;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;


@Log4j2
public class MessageStompSessionHandler extends StompSessionHandlerAdapter {

    private static Logger logger =
            LoggerFactory.getLogger(MessageStompSessionHandler.class);

    private StompClientConnection stompClientConnection;

    public MessageStompSessionHandler(StompClientConnection stompClientConnection) {
        this.stompClientConnection = stompClientConnection;
    }

    @Override
    public void afterConnected(StompSession session,
                               StompHeaders connectedHeaders) {
        logger.info("connected successfully session id {}",
                session.getSessionId());
    }

    @Override
    public void handleTransportError(StompSession session,
                                     Throwable exception) {
        if (exception instanceof ConnectionLostException) {
            try {
                logger.info("retrying connection with server");
                stompClientConnection.setupClientConnection();
            } catch (Exception e) {
                logger.error("failed to connect to server");
            }
        }
    }
}
