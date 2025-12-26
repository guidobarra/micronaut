package com.guba.example.micronaut.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.net.URL;

@Factory
public class PokeApiHttpClientConfiguration {

    private final PokeApiConfig pokeApiConfig;
    private final ApplicationConfiguration applicationConfiguration;

    public PokeApiHttpClientConfiguration(PokeApiConfig pokeApiConfig,
                                         ApplicationConfiguration applicationConfiguration) {
        this.pokeApiConfig = pokeApiConfig;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Bean
    @Named("pokeapi")
    @Singleton
    public HttpClient httpClient() throws Exception {
        // Crear configuración usando el constructor que acepta ApplicationConfiguration
        // Micronaut aplicará automáticamente la configuración desde application.yml
        // usando micronaut.http.client.pokeapi.*
        HttpClientConfiguration configuration = new HttpClientConfiguration(applicationConfiguration) {
            @Override
            public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
                return new ConnectionPoolConfiguration();
            }
        };
        configuration.setReadTimeout(pokeApiConfig.readTimeout());
        configuration.setConnectTimeout(pokeApiConfig.connectTimeout());
        
        return HttpClient.create(new URL(pokeApiConfig.url()), configuration);
    }
}

