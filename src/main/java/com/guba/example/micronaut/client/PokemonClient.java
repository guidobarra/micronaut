package com.guba.example.micronaut.client;

import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import reactor.core.publisher.Mono;

/**
 * Interface for Pokemon API clients.
 * All Pokemon API client implementations must implement this interface.
 */
public interface PokemonClient {

    /**
     * Retrieves detailed information about a specific Pokemon by its name or ID.
     *
     * @param nameOrId The Pokemon name or ID
     * @return A Mono containing the Pokemon details
     */
    Mono<Pokemon> getPokemon(String nameOrId);

    /**
     * Retrieves a paginated list of Pokemon.
     *
     * @param limit  The maximum number of results to return (default: 20)
     * @param offset The number of results to skip (default: 0)
     * @return A Mono containing the Pokemon list response
     */
    Mono<PokemonListResponse> getPokemonList(Integer limit, Integer offset);
}

