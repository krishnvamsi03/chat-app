package com.chat.message.handler.reader;


import com.chat.message.handler.store.redis.wrappers.RedisStream;
import com.chat.message.handler.websocket.MessageWebSocketClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class StreamReader {

    private MessageWebSocketClient messageWebSocketClient;

    private RedisStream<String> redisStream;

    public void readStream() {

    }
}
