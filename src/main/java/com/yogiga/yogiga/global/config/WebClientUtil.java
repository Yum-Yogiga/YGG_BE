package com.yogiga.yogiga.global.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientUtil {
    public static WebClient getBaseUrl(final String uri) {
        return WebClient.builder()
                .baseUrl(uri)
                .build();
    }
}
