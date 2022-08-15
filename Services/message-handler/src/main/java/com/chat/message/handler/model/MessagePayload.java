package com.chat.message.handler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload {
    private String sender;
    private String receiver;
    private String dateTime;
    private String message;
    private String type;
}
