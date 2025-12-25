package com.guba.example.micronaut.web.controllers;

import com.guba.example.micronaut.config.AppConfig;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

import java.net.URI;
import java.time.Instant;

@Controller
@Tag(name = "Health Check", description = "Health check endpoints")
public class HealthController {

    private static final Logger LOG = LoggerFactory.getLogger(HealthController.class);
    private final static URI SWAGGER_UI = UriBuilder.of("/swagger-ui").path("index.html").build();

    private final AppConfig appConfig;

    @Inject
    public HealthController(AppConfig appConfig) {
        this.appConfig = appConfig;
        LOG.info("HealthController initialized for application: {}", appConfig.name());
    }

    @Get(uri = "/health-check", produces = MediaType.APPLICATION_JSON)
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
        LOG.debug("Health check endpoint called");
        HealthResponse response = new HealthResponse(
            appConfig.name(),
            appConfig.version(),
            appConfig.environment(),
            "UP",
            Instant.now().toString()
        );
        LOG.info("Health check response: name={}, version={}, environment={}, status={}", 
                response.name(), response.version(), response.environment(), response.status());
        return response;
    }

    @Get
    @Hidden
    HttpResponse<?> home() {
        return HttpResponse.seeOther(SWAGGER_UI);
    }

    public record HealthResponse(
        String name,
        String version,
        String environment,
        String status,
        String timestamp
    ) {}
}

