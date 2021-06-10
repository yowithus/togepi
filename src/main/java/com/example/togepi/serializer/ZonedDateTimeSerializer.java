package com.example.togepi.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    private final String pattern;
    private final String zone;

    public ZonedDateTimeSerializer(String pattern, String zone) {
        this.pattern = pattern;
        this.zone = zone;
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        final ZonedDateTime toLocalZone = value.withZoneSameInstant(ZoneId.of(zone));
        gen.writeString(toLocalZone.format(DateTimeFormatter.ofPattern(pattern)));
    }
}
