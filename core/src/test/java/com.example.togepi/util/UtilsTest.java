package com.example.togepi.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void whenCalledisBlank_thenCorrect() {
        assertEquals(true, StringUtils.isBlank(" "));
    }

    @Test
    public void whenCalledisMixedCase_thenCorrect() {
        assertEquals(true, StringUtils.isMixedCase("abC"));
    }

    @Test
    public void whenCalledtoString_thenCorrect() {
        final String[] array = {"a", "b", "c"};
        assertEquals("{a,b,c}", ArrayUtils.toString(array));
    }

    @Test
    public void whenCalledtoStringIfArrayisNull_thenCorrect() {
        assertEquals("Array is null", ArrayUtils.toString(null, "Array is null"));
    }

    @Test
    public void whenCalledcreateNumber_thenCorrect() {
        assertEquals(123456, NumberUtils.createNumber("123456"));
    }

    @Test
    public void whenCalledcompareWithIntegers_thenCorrect() {
        assertEquals(0, NumberUtils.compare(1, 1));
    }
}
