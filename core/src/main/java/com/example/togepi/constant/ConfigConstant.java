package com.example.togepi.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigConstant {

    public static String CUSTOM_TEST_A1;

    public static String CUSTOM_TEST_B2;

    public static String CUSTOM_TEST_C3;

    @Value("${rabbitmq.test.a1}")
    public static void setCustomTestA1(String customTestA1) {
        CUSTOM_TEST_A1 = customTestA1;
    }

    @Value("${rabbitmq.test.b2}")
    public static void setCustomTestB2(String customTestB2) {
        CUSTOM_TEST_B2 = customTestB2;
    }

    @Value("${rabbitmq.test.c3}")
    public static void setCustomTestC3(String customTestC3) {
        CUSTOM_TEST_C3 = customTestC3;
    }
}