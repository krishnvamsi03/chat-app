package com.chat.websocket.endpoints;

import com.chat.websocket.model.MessagePayload;
import com.chat.websocket.model.MessageRequestSchema;
import com.chat.websocket.model.MessageResponse;
import com.chat.websocket.model.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.LinkedList;

@CrossOrigin
@Controller
public class PushMessage {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${services.message.uri}")
    private String messageUri;
    private String defaultURI = "{}{}";
    private static String API_CREATE_MESSAGE = "api/v1/message/send/false";

    @MessageMapping("/message")
    public void sendMessage(@Payload MessagePayload messagePayload) {
        simpMessagingTemplate.convertAndSendToUser(messagePayload.getReceiver(), "/queue/messages", messagePayload);
    }

    @MessageMapping("/put/message")
    public ResponseEntity<?> putMessage(@Payload MessageRequestSchema request,
                             Principal user) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = String.format(defaultURI, messageUri, API_CREATE_MESSAGE);
        HttpEntity<MessageRequestSchema> entity = new HttpEntity<>(request,
                createHttpHeader());
        ResponseEntity<?> response = restTemplate.exchange(uri,
                HttpMethod.POST, entity, ResponseEntity.class);
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
}
