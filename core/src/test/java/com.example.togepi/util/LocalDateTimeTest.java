package com.example.togepi.util;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class LocalDateTimeTest {

    @Test
    public void whenAddingDays_thenReturnCorrectPeriod() {
        final int days = 5;
        final LocalDate today = LocalDate.now();
        final LocalDate tomorrow = LocalDate.now().plusDays(days);
        final Period fiveDays = Period.ofDays(days);
        final Period period = Period.between(today, tomorrow);

        assertEquals(period, fiveDays);
    }

    @Test
    public void whenAddingSeconds_thenReturnCorrectDuration() {
        final int seconds = 3600;
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneHourLater = LocalDateTime.now().plus(Duration.ofSeconds(seconds));
        final long duration = Duration.between(now, oneHourLater).getSeconds();

        assertEquals(duration, seconds);
    }

    @Test
    public void whenChangingFormat_thenReturnCorrectResult() {
        final LocalDate localDate = LocalDate.of(1993, Month.MAY, 22);
        final String localDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        assertEquals("1993/05/22", localDateString);
    }
}
