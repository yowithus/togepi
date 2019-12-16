package com.example.togepi.config;

import com.example.togepi.mapper.MainMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MainMapper mainMapper() {
        return new MainMapper();
    }
}
