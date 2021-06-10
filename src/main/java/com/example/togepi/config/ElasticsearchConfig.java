package com.example.togepi.config;

import com.example.togepi.properties.ApplicationProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ElasticsearchConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    @Primary
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(
                        applicationProperties.getTogepi().getElasticsearch().getHost(),
                        applicationProperties.getTogepi().getElasticsearch().getPort(),
                        applicationProperties.getTogepi().getElasticsearch().getProtocol())));
    }
}
