package com.guba.example.micronaut.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppConfig(
    String name,
    String version,
    String environment
) {}

