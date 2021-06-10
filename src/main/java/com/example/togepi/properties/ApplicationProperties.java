package com.example.togepi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private Togepi togepi;

    @Data
    public static class Togepi {

        private Elasticsearch elasticsearch;

        @Data
        public static class Elasticsearch {

            private String host;
            private Integer port;
            private String protocol;
        }
    }
}
