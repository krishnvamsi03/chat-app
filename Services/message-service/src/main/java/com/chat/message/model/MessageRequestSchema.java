package com.chat.message.model;

import com.chat.message.model.Types.MessageType;
import lombok.Data;

@Data
public class MessageRequestSchema {
    private String message;
    private MessageType type;
    private String sentDateTime;
    private String senderId;
    private String receiverId;
}
