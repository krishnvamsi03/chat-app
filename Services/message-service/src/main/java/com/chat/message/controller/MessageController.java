package com.chat.message.controller;

import com.chat.message.model.MessageRequestSchema;
import com.chat.message.processor.ServiceManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    @Autowired
    private ServiceManager serviceManager;

    @PostMapping("/message/send/{isGroup}")
    public boolean publishMessage(@RequestBody MessageRequestSchema messageBody,
                                  @PathVariable boolean isGroup) {
        try {
            return serviceManager.publishMessage(messageBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
