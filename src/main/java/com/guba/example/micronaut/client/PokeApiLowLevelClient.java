package com.guba.example.micronaut.client;

import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import com.guba.example.micronaut.config.PokeApiConfig;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Singleton
@Named("PokeApiLowLevelClient")
public class PokeApiLowLevelClient implements PokemonClient {

    private static final Logger LOG = LoggerFactory.getLogger(PokeApiLowLevelClient.class);
    
    private final HttpClient httpClient;
    private final PokeApiConfig pokeApiConfig;

    public PokeApiLowLevelClient(@Named("pokeapi") HttpClient httpClient, 
                                  PokeApiConfig pokeApiConfig) {
        this.httpClient = httpClient;
        this.pokeApiConfig = pokeApiConfig;
        LOG.info("PokeApiLowLevelClient initialized with URL: {}", pokeApiConfig.url());
    }

    public Mono<Pokemon> getPokemon(String nameOrId) {
        LOG.debug("Fetching Pokemon using low-level client: {}", nameOrId);
        MutableHttpRequest<Object> request = HttpRequest.GET(pokeApiConfig.url() + "/pokemon/" + nameOrId);
        // Agregar headers desde el Map
        pokeApiConfig.headers().forEach(request::header);
        
        Flux<HttpResponse<Pokemon>> responseFlux = Flux.from(
            httpClient.exchange(request, Pokemon.class)
        );
        
        return responseFlux
                .next()
                .map(HttpResponse::body)
                .doOnNext(pokemon -> LOG.info("Retrieved Pokemon via low-level client: {} (ID: {})", 
                        pokemon.name(), pokemon.id()))
                .doOnError(error -> LOG.error("Error fetching Pokemon via low-level client: {}", nameOrId, error));
    }

    public Mono<PokemonListResponse> getPokemonList(Integer limit, Integer offset) {
        LOG.debug("Fetching Pokemon list using low-level client: limit={}, offset={}", limit, offset);
        String url = pokeApiConfig.url() + "/pokemon?limit=" + limit + "&offset=" + offset;
        MutableHttpRequest<Object> request = HttpRequest.GET(url);
        // Agregar headers desde el Map
        pokeApiConfig.headers().forEach(request::header);
        
        Flux<HttpResponse<PokemonListResponse>> responseFlux = Flux.from(
            httpClient.exchange(request, PokemonListResponse.class)
        );
        
        return responseFlux
                .next()
                .map(HttpResponse::body)
                .doOnNext(list -> LOG.info("Retrieved {} Pokemon via low-level client (total: {})", 
                        list.results().size(), list.count()))
                .doOnError(error -> LOG.error("Error fetching Pokemon list via low-level client", error));
    }
}

