package com.chat.websocket.events;

import com.chat.websocket.converter.ConvertToJson;
import com.chat.websocket.model.ActiveSessionSchema;
import com.chat.websocket.store.mongo.MongoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;


@Component
@Log4j2
public class WebSocketConnectEvent implements ApplicationListener<SessionConnectEvent> {

    private static Logger logger =
            LoggerFactory.getLogger(WebSocketConnectEvent.class);

    private static ConvertToJson convertToJson = new ConvertToJson();
    private static String ACTIVE_USER_SESSIONS = "ActiveSessions";

    @Autowired
    private MongoApi mongoApi;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        logger.info("user {} connected", event.getUser().getName());
        ActiveSessionSchema activeSessionSchema = new ActiveSessionSchema();
        activeSessionSchema.setSessionId(event.getMessage().getHeaders().get(
                "simpSessionId").toString());
        activeSessionSchema.setUserId(event.getUser().getName());
        try {
            String jsonDoc = convertToJson.getJsonString(activeSessionSchema);
            mongoApi.createDocument(mongoApi.getMongoCollection(ACTIVE_USER_SESSIONS), jsonDoc);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
