package com.app.controller;

import com.app.service.OpenWeatherClient;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final OpenWeatherClient client;

    public WeatherController(OpenWeatherClient client) {
        this.client = client;
    }

    //http://localhost:8080/api/weather/current?lat=17.3850&lon=78.4867&units=metric  -- working properly
    @GetMapping("/current")
    @Cacheable(value = "currentWeather", key = "#lat + '-' + #lon + '-' + #units")
    public Mono<ResponseEntity<JsonNode>> current(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "metric") String units
    ) {
        return client.currentWeather(lat, lon, units)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build())
                );
    }

    //http://localhost:8080/api/weather/forecast?lat=17.3850&lon=78.4867&units=metric  --working properly

    @GetMapping("/forecast")
    @Cacheable(value = "forecastWeather", key = "#lat + '-' + #lon + '-' + #units")
    public Mono<ResponseEntity<JsonNode>> forecast(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "metric") String units
    ) {
        return client.forecast(lat, lon, units)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build())
                );
    }
    //http://localhost:8080/api/weather/search?q=London                          --working properly
    @GetMapping("/search")
    public Mono<ResponseEntity<JsonNode>> search(@RequestParam String q) {
        return client.geocode(q)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build())
                );
    }
}
