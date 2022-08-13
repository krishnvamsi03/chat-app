package com.chat.message.model;

import com.chat.message.model.Types.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageContentSchema {
    private String content;
    private MessageType type;
}
