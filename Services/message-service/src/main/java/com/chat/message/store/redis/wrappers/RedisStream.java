package com.chat.message.store.redis.wrappers;

import io.lettuce.core.RedisBusyException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadGroupArgs;

import java.util.stream.Stream;

@Log4j2
public class RedisStream<V> {
    private RStream<String, V> stream;
    private String key;

    @AllArgsConstructor
    @Data
    public static class StreamData<V> {
        private StreamMessageId messageId;
        private V value;
    }

    public RedisStream(RStream<String, V> stream, String key) {
        this.stream = stream;
        this.key = key;
    }

    public void createGroup(String groupName) {
        try {
            stream.createGroup(groupName, new StreamMessageId(0));
        } catch (RedisBusyException e) {
            log.debug("Group {} already exits", groupName);
        }
    }

    public StreamMessageId add(V value) {
        return stream.add(StreamAddArgs.entry(key, value));
    }

    public void remove(StreamMessageId streamMessageId) {
        stream.remove(streamMessageId);
    }

    public void ack(StreamMessageId streamMessageId, String groupName) {
        stream.ack(groupName, streamMessageId);
    }

    public Stream<StreamData> readGroup(String groupName, String consumerName
            , StreamReadGroupArgs groupArgs) {
        return stream.readGroup(groupName, consumerName,
                        groupArgs)
                .entrySet()
                .stream()
                .map(entry -> new StreamData(entry.getKey(),
                        entry.getValue().get(key)));
    }
}
