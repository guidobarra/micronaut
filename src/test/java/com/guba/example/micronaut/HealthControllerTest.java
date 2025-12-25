package com.guba.example.micronaut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import java.util.Map;

@MicronautTest
public class HealthControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testHealth() {
        HttpResponse<Map> response = client.toBlocking().exchange(
            HttpRequest.GET("/health-check"),
            Map.class
        );

        assertEquals(200, response.code());
        Map<String, Object> body = response.body();
        
        assertNotNull(body);
        assertNotNull(body.get("name"));
        assertNotNull(body.get("version"));
        assertNotNull(body.get("environment"));
        assertEquals("UP", body.get("status"));
        assertNotNull(body.get("timestamp"));
    }
}

