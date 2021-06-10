package com.example.togepi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorUtil {

    public static String encode(Object[] values) {
        final String s = StringUtils.join(values, ",");
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static Object[] decode(String nextCursor) {
        final byte[] decoded = Base64.getDecoder().decode(nextCursor);
        return new String(decoded).split(",");
    }
}
