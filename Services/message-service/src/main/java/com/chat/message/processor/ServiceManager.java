package com.chat.message.processor;

import com.chat.message.Builder.MessageBodyBuilder;
import com.chat.message.model.MessageRequestSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager {

    @Autowired
    private PublishMessage publishMessage;
    private MessageBodyBuilder messageBodyBuilder;

    public ServiceManager(MessageBodyBuilder messageBodyBuilder) {
        this.messageBodyBuilder = messageBodyBuilder;
    }

    private boolean validateRequest(MessageRequestSchema messageRequestSchema) {
        boolean res = true;
        if (messageRequestSchema == null) {
            res = false;
            return res;
        }
        boolean validMessage =
                messageRequestSchema.getMessage() == null || messageRequestSchema.getMessage().length() == 0;
        boolean validReceiverId =
                messageRequestSchema.getReceiverId() == null || messageRequestSchema.getReceiverId().length() == 0;
        boolean validSenderId =
                messageRequestSchema.getSenderId() == null || messageRequestSchema.getSenderId().length() == 0;
        boolean validDateTime =
                messageRequestSchema.getSentDateTime() == null || messageRequestSchema.getSentDateTime().length() == 0;
        boolean validContent = messageRequestSchema.getType() == null;
        res = !(validContent || validReceiverId || validMessage || validSenderId || validDateTime);
        return res;
    }

    public boolean publishMessage(MessageRequestSchema messageRequestSchema) throws JsonProcessingException {
        if (validateRequest(messageRequestSchema)) {
            messageBodyBuilder = messageBodyBuilder.build(messageRequestSchema);
            publishMessage.createMessage(messageBodyBuilder);
            return true;
        }
        return false;
    }
}
