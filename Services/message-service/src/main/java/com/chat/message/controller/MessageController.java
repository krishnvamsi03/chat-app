package com.chat.message.controller;

import com.chat.message.model.Login;
import com.chat.message.model.MessageRequestSchema;
import com.chat.message.model.MessageResponse;
import com.chat.message.processor.ServiceManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/message/send/{isGroup}")
    public ResponseEntity<?> publishMessage(@RequestBody MessageRequestSchema messageBody,
                                            @PathVariable boolean isGroup) {
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login login) {
        MessageResponse messageResponse = serviceManager.getUserId(login);
        if (messageResponse.getStatusCode() == 200) {
            return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(messageResponse,
                    HttpStatus.BAD_REQUEST);
        }
    }

}
