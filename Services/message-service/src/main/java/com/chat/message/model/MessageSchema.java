package com.chat.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSchema {
    private String messageContentSchema;
    private String sentDateTime;
    private String receivedDateTime;
}
