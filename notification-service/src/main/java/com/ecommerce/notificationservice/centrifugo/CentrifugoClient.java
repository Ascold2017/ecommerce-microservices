package com.ecommerce.notificationservice.centrifugo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class CentrifugoClient {

    private final RestClient restClient;

    public CentrifugoClient(
            @Value("${centrifugo.api-url}") String apiUrl,   // http://localhost:8000
            @Value("${centrifugo.api-key}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-API-Key", apiKey)
                .build();
    }

    public void publish(String channel, Object data) {
        restClient.post()
                .uri("/api/publish")   // держим /api в uri, не в baseUrl (иначе ведущий '/' его срежет)
                .body(Map.of("channel", channel, "data", data))
                .retrieve()
                .toBodilessEntity();   // учебно: ответ не разбираем; в проде — проверять поле error
    }
}