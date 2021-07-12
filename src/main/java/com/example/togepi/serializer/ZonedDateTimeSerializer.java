package com.example.togepi.serializer;

import com.example.togepi.constant.DateTimeConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value
                .withZoneSameInstant(DateTimeConstant.DEFAULT_ZONE)
                .format(DateTimeConstant.DEFAULT_DATE_TIME_FORMAT)
        );
    }
}
