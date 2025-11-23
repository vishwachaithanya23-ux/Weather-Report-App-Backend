package com.example.weather.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenWeatherClient {
    private final WebClient webClient;
    private final String apiKey;

    public OpenWeatherClient(@Value("${openweathermap.base-url}") String baseUrl,
                             @Value("${openweathermap.api-key}") String apiKey) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Mono<JsonNode> oneCall(double lat, double lon, String units) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/onecall")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("units", units == null ? "metric" : units)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> geocode(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/geo/1.0/direct")
                        .queryParam("q", city)
                        .queryParam("limit", 6)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}
