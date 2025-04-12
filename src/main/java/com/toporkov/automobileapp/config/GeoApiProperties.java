package com.toporkov.automobileapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.geocoding")
@Data
public class GeoApiProperties {

    private String url;
    private String apiKey;
    private int timeout;
    private Retry retry;

    @Data
    public static class Retry {
        private int maxAttempts;
        private long backoff;
    }
}
