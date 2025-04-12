package com.toporkov.automobileapp.config;

import com.toporkov.automobileapp.client.GeoApiClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(GeoApiProperties.class)
public class GeoApiConfig {

    @Bean
    public GeoApiClient geoCodingClient(final GeoApiProperties properties,
                                        final RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .rootUri(properties.getUrl())
                .connectTimeout(Duration.ofMillis(properties.getTimeout()))
                .build();

        return new GeoApiClient(restTemplate, properties.getUrl(), properties.getApiKey());
    }
}
