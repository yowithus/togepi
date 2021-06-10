package com.example.togepi.util;

import com.example.togepi.constant.DateTimeConstant;
import com.example.togepi.exception.ServerException;
import com.example.togepi.serializer.ZonedDateTimeSerializer;
import com.example.togepi.type.ErrorType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class MapperUtil {

    public ObjectMapper getObjectMapper() {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        final ZonedDateTimeSerializer zonedDateTimeSerializer = new ZonedDateTimeSerializer(
                        DateTimeConstant.DEFAULT_DATE_TIME_FORMAT, DateTimeConstant.ASIA_JAKARTA_TIMEZONE);
        javaTimeModule.addSerializer(ZonedDateTime.class, zonedDateTimeSerializer);

        return new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                .registerModule(javaTimeModule);
    }

    public ObjectMapper getDefaultObjectMapper() {
        return getObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public <T> T convertJsonToObject(String json, Class<T> valueType) {
        return fromJson(getObjectMapper(), json, valueType);
    }

    public <T> T convertSnakeCaseJsonToObject(String json, Class<T> valueType) {
        return fromJson(
                getDefaultObjectMapper(), json, valueType);
    }

    public <T> T fromJson(ObjectMapper objectMapper, String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new ServerException(ErrorType.PARSE_JSON_ERROR, e);
        }
    }
}
