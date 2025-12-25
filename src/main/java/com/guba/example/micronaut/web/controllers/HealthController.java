package com.guba.example.micronaut.web.controllers;

import com.guba.example.micronaut.config.AppConfig;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import java.time.Instant;

@Controller("/health")
@Tag(name = "Health", description = "Health check endpoints")
public class HealthController {

    private final AppConfig appConfig;

    @Inject
    public HealthController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Health check",
            description = "Returns the health status of the application including name, version, environment, status, and timestamp"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Application is healthy",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HealthResponse.class))
    )
    public HealthResponse health() {
        return new HealthResponse(
            appConfig.name(),
            appConfig.version(),
            appConfig.environment(),
            "UP",
            Instant.now().toString()
        );
    }

    public record HealthResponse(
        String name,
        String version,
        String environment,
        String status,
        String timestamp
    ) {}
}

