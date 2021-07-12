package com.example.togepi.serializer;

import com.example.togepi.constant.DateTimeConstant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return ZonedDateTime.parse(jsonParser.getText(), DateTimeConstant.DEFAULT_DATE_TIME_FORMAT)
                .withZoneSameInstant(DateTimeConstant.DEFAULT_ZONE);
    }
}
