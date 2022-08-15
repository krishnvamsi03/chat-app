package com.chat.message.processor;

import com.chat.message.Builder.MessageBodyBuilder;
import com.chat.message.model.Login;
import com.chat.message.model.MessageRequestSchema;
import com.chat.message.model.MessageResponse;
import com.chat.message.store.mongo.MongoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class ServiceManager {

    @Autowired
    private PublishMessage publishMessage;

    @Autowired
    private MongoApi mongoApi;

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

    public MessageResponse publishMessage(MessageRequestSchema messageRequestSchema) throws JsonProcessingException {
        if (validateRequest(messageRequestSchema)) {
            messageBodyBuilder = messageBodyBuilder.build(messageRequestSchema);
            return publishMessage.createMessage(messageBodyBuilder);
        }
        return new MessageResponse(Instant.now().toString(), null, 400, "Some" +
                " inputs are missing");
    }

    public MessageResponse getUserId(Login login) {
        Document doc1 = mongoApi.getDocumentOnEq("user",
                "username", login.getUsername());
        Document doc2 = mongoApi.getDocumentOnEq("user", "password",
                login.getPassword());
        if (doc1 == null || doc2 == null) {
            return new MessageResponse(Instant.now().toString(), "", 400, "no" +
                    " user found");
        } else {
            return new MessageResponse(Instant.now().toString(),
                    doc1.getObjectId("_id").toString(), 200, "user found");
        }
    }
}
