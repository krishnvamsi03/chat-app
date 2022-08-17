package com.chat.websocket.endpoints;

import com.chat.websocket.model.MessagePayload;
import com.chat.websocket.model.MessageRequestSchema;
import com.chat.websocket.model.MessageResponse;
import com.chat.websocket.store.mongo.MongoApi;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;

@CrossOrigin
@Controller
public class PushMessage {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MongoApi mongoApi;

    @Value("${services.message.uri}")
    private String messageUri;
    private String defaultURI = "%s%s";
    private static String API_CREATE_MESSAGE = "api/v1/message/send/false";

    private static String ACTIVE_USER_SESSIONS = "ActiveSessions";


    @MessageMapping("/message")
    public void sendMessage(@Payload MessagePayload messagePayload) {
        simpMessagingTemplate.convertAndSendToUser(messagePayload.getReceiver(), "/queue/messages", messagePayload);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAvailableUser() {
        simpMessagingTemplate.convertAndSend("/topic/availableUsers", getAvailableUsers());
    }

    @MessageMapping("/put/message")
    public ResponseEntity<?> putMessage(@Payload MessageRequestSchema request) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = String.format(defaultURI, messageUri, API_CREATE_MESSAGE);
        HttpEntity<MessageRequestSchema> entity = new HttpEntity<>(request,
                createHttpHeader());
        ResponseEntity<?> response = restTemplate.exchange(uri,
                HttpMethod.POST, entity, MessageResponse.class);
        return response;
    }

    private HttpHeaders createHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedList<MediaType> acceptedType = new LinkedList<>();
        acceptedType.add(MediaType.APPLICATION_JSON);
        acceptedType.add(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setAccept(acceptedType);
        httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; " +
                "x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840" +
                ".99 Safari/537.36");
        return httpHeaders;
    }

    private HashMap<String, String> getAvailableUsers() {
        MongoCollection<Document> mongoCollection =
                mongoApi.getMongoCollection(ACTIVE_USER_SESSIONS);
        FindIterable<Document> documents = mongoCollection.find();
        MongoCursor<Document> cursor = documents.cursor();
        HashMap<String, String> users = new HashMap<>();
        try {
            while (cursor.hasNext()) {
                String userId = cursor.next().getString("userId");
                Document document =
                        mongoApi.getDocumentOnEq("user", "_id",
                                new ObjectId(userId));
                users.put(document.getObjectId("_id").toString(),
                        document.get("username").toString());
            }
        } finally {
            cursor.close();
        }
        return users;
    }
}
