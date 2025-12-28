package com.guba.example.micronaut.client;

import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import com.guba.example.micronaut.config.PokeApiConfig;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.reactor.http.client.ReactorHttpClient;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
@Named("pokeApiReactorClient")
public class PokeApiReactorClient implements PokemonClient {

    private static final Logger LOG = LoggerFactory.getLogger(PokeApiReactorClient.class);
    
    private final ReactorHttpClient httpClient;
    private final PokeApiConfig pokeApiConfig;

    public PokeApiReactorClient(ReactorHttpClient httpClient, 
                                PokeApiConfig pokeApiConfig) {
        this.httpClient = httpClient;
        this.pokeApiConfig = pokeApiConfig;
        LOG.info("PokeApiReactorClient initialized with URL: {}", pokeApiConfig.url());
    }

    public Mono<Pokemon> getPokemon(String nameOrId) {
        LOG.debug("Fetching Pokemon using ReactorHttpClient: {}", nameOrId);
        MutableHttpRequest<Object> request = HttpRequest.GET(pokeApiConfig.url() + "/pokemon/" + nameOrId);
        pokeApiConfig.headers().forEach(request::header);
        
        return httpClient.exchange(request, Pokemon.class)
                .map(HttpResponse::body)
                .doOnNext(pokemon -> LOG.info("Retrieved Pokemon via ReactorHttpClient: {} (ID: {})", 
                        pokemon.name(), pokemon.id()))
                .doOnError(error -> LOG.error("Error fetching Pokemon via ReactorHttpClient: {}", nameOrId, error));
    }

    public Mono<PokemonListResponse> getPokemonList(Integer limit, Integer offset) {
        LOG.debug("Fetching Pokemon list using ReactorHttpClient: limit={}, offset={}", limit, offset);
        String url = pokeApiConfig.url() + "/pokemon?limit=" + limit + "&offset=" + offset;
        MutableHttpRequest<Object> request = HttpRequest.GET(url);
        pokeApiConfig.headers().forEach(request::header);
        
        return httpClient.exchange(request, PokemonListResponse.class)
                .map(response -> response.body())
                .doOnNext(list -> LOG.info("Retrieved {} Pokemon via ReactorHttpClient (total: {})", 
                        list.results().size(), list.count()))
                .doOnError(error -> LOG.error("Error fetching Pokemon list via ReactorHttpClient", error));
    }
}

