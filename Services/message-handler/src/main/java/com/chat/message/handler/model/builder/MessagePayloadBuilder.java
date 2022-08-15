package com.chat.message.handler.model.builder;

import com.chat.message.handler.model.MessagePayload;

public class MessagePayloadBuilder {
    private String sender;
    private String receiver;
    private String dateTime;
    private String message;
    private String type;

    public MessagePayloadBuilder withSender(String sender) {
        this.sender = sender;
        return this;
    }

    public MessagePayloadBuilder withReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public MessagePayloadBuilder withSentDateTime(String sentTime) {
        this.dateTime = sentTime;
        return this;
    }

    public MessagePayloadBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public MessagePayloadBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public MessagePayload build() {
        return new MessagePayload(sender, receiver, dateTime, message, type);
    }
}
