package com.chat.message.Builder;

import com.chat.message.model.DeliveryStatus;
import com.chat.message.model.MessageSchema;

public class DeliveryStatusBuilder {
    private String sender;
    private String receiver;
    private String messageSchema;
    private boolean isRecieved;
    private String receivedTime;

    public DeliveryStatusBuilder withSender(String sender) {
        this.sender = sender;
        return this;
    }

    public DeliveryStatusBuilder withReciever(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public DeliveryStatusBuilder withMessageSchema(String messageSchema) {
        this.messageSchema = messageSchema;
        return this;
    }

    public DeliveryStatusBuilder withIsReceived(boolean isReceived) {
        this.isRecieved = isReceived;
        return this;
    }

    public DeliveryStatusBuilder withReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }

    public DeliveryStatus build() {
        return new DeliveryStatus(sender, receiver, messageSchema, isRecieved, receivedTime);
    }

}
