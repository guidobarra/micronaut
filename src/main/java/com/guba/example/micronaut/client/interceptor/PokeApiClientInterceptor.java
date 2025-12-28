package com.guba.example.micronaut.client.interceptor;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * HTTP Client Filter para interceptar peticiones del cliente declarativo PokeApiClient.
 * Se ejecuta antes (pre) y después (post) de cada petición HTTP.
 * Los headers y timeouts están configurados mediante @Header y application.yml.
 */
@Singleton
@Filter
public class PokeApiClientInterceptor implements HttpClientFilter {

    private static final Logger LOG = LoggerFactory.getLogger(PokeApiClientInterceptor.class);

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        // Verificar si la petición es para pokeapi.co
        String uri = request.getUri().toString();
        if (!uri.contains("pokeapi.co")) {
            // Si no es para pokeapi.co, continuar sin modificar
            return chain.proceed(request);
        }

        // PRE-PROCESSING: Antes de enviar la petición
        // Los headers ya están configurados mediante @Header en PokeApiClient
        LOG.info("=== PRE-REQUEST INTERCEPTOR ===");
        LOG.info("Request URI: {}", request.getUri());
        LOG.info("Request Method: {}", request.getMethod());
        LOG.info("Request Headers: {}", request.getHeaders().asMap());
        
        // POST-PROCESSING: Después de recibir la respuesta
        return Flux.from(chain.proceed(request))
                .doOnNext(response -> {
                    LOG.info("=== POST-RESPONSE INTERCEPTOR ===");
                    LOG.info("Response Status: {}", response.status());
                    LOG.info("Response Headers: {}", response.getHeaders().asMap());
                })
                .doOnError(error -> {
                    LOG.error("=== ERROR INTERCEPTOR ===");
                    LOG.error("Error during request: {}", error.getMessage(), error);
                });
    }
}

