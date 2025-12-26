package com.guba.example.micronaut.config;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@ConfigurationProperties("app.pokeapi")
public record PokeApiConfig(
    String url,
    Duration readTimeout,
    Duration connectTimeout,

    Map<String, String> headers
) {
}

