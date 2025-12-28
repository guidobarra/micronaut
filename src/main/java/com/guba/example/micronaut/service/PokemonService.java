package com.guba.example.micronaut.service;

import com.guba.example.micronaut.client.PokemonClient;
import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
public class PokemonService {

    private static final Logger LOG = LoggerFactory.getLogger(PokemonService.class);
    private final PokemonClient pokemonClient;

    //pokeApiClient
    //pokeApiLowLevelClient
    //pokeApiReactorClient
    public PokemonService(@Named("pokeApiReactorClient") PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
        LOG.info("=== PokemonService initialized with client: {} ===", this.pokemonClient);
    }

    public Mono<Pokemon> getPokemon(String nameOrId) {
        LOG.debug("Fetching Pokemon: {}", nameOrId);
        return pokemonClient.getPokemon(nameOrId)
                .doOnNext(pokemon -> LOG.info("Retrieved Pokemon: {} (ID: {})", pokemon.name(), pokemon.id()))
                .doOnError(error -> LOG.error("Error fetching Pokemon: {}", nameOrId, error));
    }

    public Mono<PokemonListResponse> getPokemonList(Integer limit, Integer offset) {
        LOG.debug("Fetching Pokemon list: limit={}, offset={}", limit, offset);
        return pokemonClient.getPokemonList(limit, offset)
                .doOnNext(response -> LOG.info("Retrieved {} Pokemon (total: {})", response.results().size(), response.count()))
                .doOnError(error -> LOG.error("Error fetching Pokemon list", error));
    }
}

