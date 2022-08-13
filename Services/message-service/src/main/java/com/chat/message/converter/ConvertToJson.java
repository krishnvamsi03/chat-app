package com.chat.message.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConvertToJson<V> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public String getJsonString(V object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
