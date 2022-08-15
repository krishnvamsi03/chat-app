package com.chat.message.processor;

import com.chat.message.Builder.MessageBodyBuilder;
import com.chat.message.converter.ConvertToJson;
import com.chat.message.model.MessageRequestSchema;
import com.chat.message.model.MessageResponse;
import com.chat.message.store.mongo.MongoApi;
import com.chat.message.store.redis.wrappers.RedisStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.result.InsertOneResult;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

public class PublishMessage {
    @Autowired
    private MongoApi mongoApi;

    private ConvertToJson convertToJson;

    private RedisStream<String> redisStream;

    private static String MESSAGE_CONTENT = "MessageContent";
    private static String MESSAGES = "Messages";
    private static String DELIVERY_STATUS = "DeliveryStatus";


    public PublishMessage(RedisStream<String> redisStream) {
        this.convertToJson = new ConvertToJson();
        this.redisStream = redisStream;
    }

    public MessageResponse createMessage(MessageBodyBuilder messageBodyBuilder) throws JsonProcessingException {
        InsertOneResult messageContentRes = insertDoc(MESSAGE_CONTENT,
                convertToJson.getJsonString(messageBodyBuilder.getMessageContentSchema()));
        messageBodyBuilder.getMessageSchema().setMessageContentSchema(getId(messageContentRes));

        InsertOneResult messageRes = insertDoc(MESSAGES,
                convertToJson.getJsonString(messageBodyBuilder.getMessageSchema()));
        messageBodyBuilder.getDeliveryStatus().setMessageSchema(getId(messageRes));

        InsertOneResult deliveryRes =
                mongoApi.createDocument(mongoApi.getMongoCollection(DELIVERY_STATUS),
                        convertToJson.getJsonString(messageBodyBuilder.getDeliveryStatus()));
        redisStream.add(getId(deliveryRes));
        return new MessageResponse(Instant.now().toString(),
                deliveryRes.getInsertedId().asObjectId().getValue().toString(), 202, "successfully added to stream");
    }

    private String getId(InsertOneResult res) {
        return res.getInsertedId().asObjectId().getValue().toString();
    }

    private InsertOneResult insertDoc(String collectionName, String jsonStr) {
        return mongoApi.createDocument(mongoApi.getMongoCollection(collectionName), jsonStr);
    }
}
