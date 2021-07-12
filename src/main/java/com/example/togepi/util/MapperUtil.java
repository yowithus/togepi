package com.example.togepi.util;

import com.example.togepi.exception.ServerException;
import com.example.togepi.serializer.ZonedDateTimeDeserializer;
import com.example.togepi.serializer.ZonedDateTimeSerializer;
import com.example.togepi.type.ErrorType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class MapperUtil {

    public ObjectMapper getObjectMapper() {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());

        return new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(javaTimeModule);
    }

    public <T> T fromJson(String json, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(json, valueType);
        } catch (IOException e) {
            throw new ServerException(ErrorType.PARSE_JSON_ERROR, e);
        }
    }

    public String toJson(Object body) {
        try {
            return getObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new ServerException(ErrorType.PARSE_JSON_ERROR, e);
        }
    }
}
