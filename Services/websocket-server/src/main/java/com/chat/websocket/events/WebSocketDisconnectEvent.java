package com.chat.websocket.events;

import com.chat.websocket.store.mongo.MongoApi;
import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {

    private static Logger logger =
            LoggerFactory.getLogger(WebSocketDisconnectEvent.class);

    private static String ACTIVE_USER_SESSIONS = "ActiveSessions";

    @Autowired
    private MongoApi mongoApi;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        logger.info("user {} with session id {} disconnected ",
                event.getUser().getName(), event.getSessionId());
        DeleteResult result =
                mongoApi.deleteDocument(mongoApi.getMongoCollection(ACTIVE_USER_SESSIONS), "sessionId", event.getSessionId());
        if (result.getDeletedCount() == 0) {
            logger.error("Failed to delete session from active session {} ",
                    event.getSessionId());
        }
    }
}
