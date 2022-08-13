package com.chat.message.Builder;

import com.chat.message.model.DeliveryStatus;
import com.chat.message.model.MessageContentSchema;
import com.chat.message.model.MessageRequestSchema;
import com.chat.message.model.MessageSchema;
import lombok.Getter;

public class MessageBodyBuilder {
    private MessageContentBuilder messageContentBuilder;
    private MessageBuilder messageBuilder;
    private DeliveryStatusBuilder deliveryStatusBuilder;

    @Getter
    private MessageContentSchema messageContentSchema;
    @Getter
    private MessageSchema messageSchema;
    @Getter
    private DeliveryStatus deliveryStatus;

    public MessageBodyBuilder() {
        this.messageContentBuilder = new MessageContentBuilder();
        this.messageBuilder = new MessageBuilder();
        this.deliveryStatusBuilder = new DeliveryStatusBuilder();
    }

    public MessageBodyBuilder build(MessageRequestSchema messageRequestSchema) {
        messageContentSchema = buildMessageContent(messageRequestSchema);
        messageSchema = buildMessageSchema(messageRequestSchema,
                messageContentSchema);
        deliveryStatus = buildDeliveryStatus(messageRequestSchema,
                messageSchema);
        return this;
    }

    private MessageContentSchema buildMessageContent(MessageRequestSchema messageRequestSchema) {
        return messageContentBuilder.withMessageContent(messageRequestSchema.getMessage()).
                withMessageType(messageRequestSchema.getType()).build();
    }

    private MessageSchema buildMessageSchema(MessageRequestSchema messageRequestSchema, MessageContentSchema messageContentSchema) {
        return messageBuilder
                .withSentDatetime(messageRequestSchema.getSentDateTime()).build();
    }

    private DeliveryStatus buildDeliveryStatus(MessageRequestSchema messageRequestSchema, MessageSchema messageSchema) {
        return deliveryStatusBuilder.withSender(messageRequestSchema.getSenderId())
                .withReciever(messageRequestSchema.getReceiverId())
                .build();
    }

}
