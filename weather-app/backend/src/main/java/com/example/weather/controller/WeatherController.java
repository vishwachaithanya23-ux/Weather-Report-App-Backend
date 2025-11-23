package com.example.weather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.example.weather.client.OpenWeatherClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final OpenWeatherClient client;

    public WeatherController(OpenWeatherClient client) {
        this.client = client;
    }

    @GetMapping("/current")
    @Cacheable(value = "currentWeather", key = "#lat + '-' + #lon + '-' + #units", unless = "#result == null")
    public Mono<ResponseEntity<JsonNode>> current(@RequestParam double lat,
                                                  @RequestParam double lon,
                                                  @RequestParam(required = false, defaultValue = "metric") String units) {
        return client.oneCall(lat, lon, units)
                .map(body -> ResponseEntity.ok(body))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()));
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<JsonNode>> search(@RequestParam String q) {
        return client.geocode(q)
                .map(body -> ResponseEntity.ok(body))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()));
    }
}
