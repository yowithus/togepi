package com.example.togepi.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void whenCalledisBlank_thenCorrect() {
        assertEquals(StringUtils.isBlank(" "), true);
    }

    @Test
    public void whenCalledisMixedCase_thenCorrect() {
        assertEquals(StringUtils.isMixedCase("abC"), true);
    }

    @Test
    public void whenCalledtoString_thenCorrect() {
        final String[] array = {"a", "b", "c"};
        assertEquals(ArrayUtils.toString(array), "{a,b,c}");
    }

    @Test
    public void whenCalledtoStringIfArrayisNull_thenCorrect() {
        assertEquals(ArrayUtils.toString(null, "Array is null"), "Array is null");
    }

    @Test
    public void whenCalledcreateNumber_thenCorrect() {
        assertEquals(NumberUtils.createNumber("123456"), 123456);
    }

    @Test
    public void whenCalledcompareWithIntegers_thenCorrect() {
        assertEquals(NumberUtils.compare(1, 1), 0);
    }
}
