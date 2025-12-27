package com.alura.literalura.mapper;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class JsonMapper {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T fromJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
