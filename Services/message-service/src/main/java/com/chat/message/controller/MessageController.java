package com.chat.message.controller;

import com.chat.message.model.Login;
import com.chat.message.model.MessageRequestSchema;
import com.chat.message.model.MessageResponse;
import com.chat.message.processor.ServiceManager;
import com.chat.message.utility.Jwtutitliy;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MessageController {

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private Jwtutitliy jwtutitliy;

    @PostMapping("/message/send/{isGroup}")
    public ResponseEntity<?> publishMessage(@RequestBody MessageRequestSchema messageBody,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                            @PathVariable boolean isGroup) {
        String token = authorizationHeader.split(" ")[1];
        if (!jwtutitliy.validateToken(token)) {
            return new ResponseEntity<>("Not a valid token, token expired or maybe bad credentials",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            MessageResponse response =
                    serviceManager.publishMessage(messageBody);
            if (response.getStatusCode() == 202) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else if (response.getStatusCode() == 400) {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (JsonProcessingException e) {
        }
        return new ResponseEntity<>("Failed to parse data",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/chats/{userId}")
    public ResponseEntity<?> getUserChats(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                          String userId) {
        return null;
    }

    @GetMapping("/chats/users/{sender}/{receiver}")
    public ResponseEntity<?> getUsersChats(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                           String sender, String receiver) {
        return null;
    }

    @GetMapping("/chats/group/{groupId}")
    public ResponseEntity<?> getGroupChats(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                           String groupId) {
        return null;
    }

    @GetMapping("/messages/{userId}")
    public ResponseEntity<?> getUserMessages(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return null;
    }

}
