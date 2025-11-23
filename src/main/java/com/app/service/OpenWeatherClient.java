package com.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenWeatherClient {

    private final WebClient webClient;
    private final String apiKey;

    public OpenWeatherClient(
            @Value("${openweathermap.base-url}") String baseUrl,
            @Value("${openweathermap.api-key}") String apiKey
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    /**
     * ✔ FREE — Current Weather API
     */
    public Mono<JsonNode> currentWeather(double lat, double lon, String units) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("units", units)
                        .queryParam("appid", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    /**
     * ✔ FREE — 5-Day / 3-Hour Forecast API
     */
    public Mono<JsonNode> forecast(double lat, double lon, String units) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("units", units)
                        .queryParam("appid", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class);
    }


    public Mono<JsonNode> geocode(String city) {
        return WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.openweathermap.org")
                        .path("/geo/1.0/direct")
                        .queryParam("q", city)
                        .queryParam("limit", 5)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

}
