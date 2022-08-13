package com.chat.message.Builder;

import com.chat.message.model.MessageContentSchema;
import com.chat.message.model.MessageSchema;

public class MessageBuilder {
    private String messageContentSchema;
    private String sentDateTime;
    private String receivedDateTime;

    public MessageBuilder withMessageContentSchema(String messageContentSchema) {
        this.messageContentSchema = messageContentSchema;
        return this;
    }

    public MessageBuilder withSentDatetime(String datetime) {
        this.sentDateTime = datetime;
        return this;
    }

    public MessageBuilder withReceivedDateTime(String dateTime) {
        this.receivedDateTime = dateTime;
        return this;
    }

    public MessageSchema build() {
        return new MessageSchema(this.messageContentSchema, this.sentDateTime
                , this.receivedDateTime);
    }

}
