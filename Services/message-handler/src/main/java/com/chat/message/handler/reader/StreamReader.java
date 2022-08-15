package com.chat.message.handler.reader;


import com.chat.message.handler.model.MessagePayload;
import com.chat.message.handler.model.builder.MessagePayloadBuilder;
import com.chat.message.handler.store.mongo.MongoApi;
import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.MessageStompSessionHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.stream.Stream;

@AllArgsConstructor
@Log4j2
public class StreamReader {

    private static Logger logger = LoggerFactory.getLogger(StreamReader.class);
    private RedisStream<String> redisStream;
    private StompSession stompSession;
    private MongoApi mongoApi;
    private static String GROUP_NAME = "MessagesGroup";
    private static String CONSUMER_NAME = "CONSUMER-1";
    private static String DELIVERY_COLLECTION = "DeliveryStatus";
    private static String MESSAGE_CONTENT = "MessageContent";
    private static String MESSAGES = "Messages";
    private static String ID = "_id";
    private static String ACTIVE_USER_SESSIONS = "ActiveSessions";
    private static String RECEIVER = "receiver";
    private static String MESSAGE_SCHEMA = "messageSchema";
    private static String MESSAGE_CONTENT_SCHEMA = "messageContentSchema";
    private static String CONTENT = "content";
    private static String TYPE = "type";
    private static String SENT_DATE_TIME = "sentDateTime";
    private static String SENDER = "sender";
    private static String DESTINATION = "/app/message";


    public void readStream() {
        Stream<RedisStream.StreamData> data = redisStream.readGroup(
                GROUP_NAME, CONSUMER_NAME,
                StreamReadGroupArgs.neverDelivered());
        data.forEach(message -> {
            Document delDoc = mongoApi.getDocumentOnEq(DELIVERY_COLLECTION, ID,
                    new ObjectId(message.getValue().toString()));
            if (checkUserOnline(delDoc.get(RECEIVER).toString())) {
                Document messageDoc = mongoApi.getDocumentOnEq(MESSAGES, ID,
                        new ObjectId(delDoc.get(MESSAGE_SCHEMA).toString()));
                Document messageContentDoc =
                        mongoApi.getDocumentOnEq(MESSAGE_CONTENT, ID,
                                new ObjectId(messageDoc.get(MESSAGE_CONTENT_SCHEMA
                                ).toString()));
                StompSession.Receiptable receiptable = stompSession.send(
                        DESTINATION, createPayload(delDoc,
                        messageDoc, messageContentDoc));
                if (receiptable.getReceiptId() != null || receiptable.getReceiptId().length() > 0) {
                    redisStream.ack(message.getMessageId(), GROUP_NAME);
                    logger.info("message acknowledge received");
                }
            }
        });
    }

    private boolean checkUserOnline(String user) {
        logger.info("check user {} availability ", user);
        Document doc = mongoApi.getDocumentOnEq(ACTIVE_USER_SESSIONS, ID,
                new ObjectId(user));

        return doc == null ? false : true;
    }

    private MessagePayload createPayload(Document delDoc, Document messageDoc
            , Document messageContentDoc) {
        MessagePayload payload =
                new MessagePayloadBuilder().withSender(delDoc.getString(SENDER)).
                        withReceiver(delDoc.getString(RECEIVER)).
                        withMessage(messageContentDoc.getString(CONTENT)).
                        withType(messageContentDoc.getString(TYPE)).
                        withSentDateTime(messageDoc.getString(SENT_DATE_TIME)).
                        build();
        return payload;
    }
}
