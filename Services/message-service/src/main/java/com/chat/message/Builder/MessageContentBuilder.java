package com.chat.message.Builder;

import com.chat.message.model.MessageContentSchema;
import com.chat.message.model.Types.MessageType;

public class MessageContentBuilder {
    private String messageContent;
    private MessageType messageType;

    public MessageContentBuilder withMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public MessageContentBuilder withMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageContentSchema build() {
        return new MessageContentSchema(this.messageContent, this.messageType);
    }
}
