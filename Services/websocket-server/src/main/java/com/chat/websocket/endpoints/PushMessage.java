package com.chat.websocket.endpoints;

import com.chat.websocket.model.MessageRequestSchema;
import com.chat.websocket.model.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.LinkedList;

@Controller
public class PushMessage {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendToUser("/queue/messages")
    public ResponseBody sendMessage(@Payload String msg, Principal user) {
        return new ResponseBody(user.getName(), msg);
    }

    @MessageMapping("/put/message")
    public String putMessage(@Payload MessageRequestSchema request,
                                   Principal user) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8081/api/v1/message/send/false";
        HttpEntity<MessageRequestSchema> entity = new HttpEntity<>(request,
                createHttpHeader());
        ResponseEntity<?> response = restTemplate.exchange(uri,
                HttpMethod.POST, entity, Boolean.class);
        return response.toString();
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
