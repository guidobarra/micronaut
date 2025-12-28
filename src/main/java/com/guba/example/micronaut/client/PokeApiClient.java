package com.guba.example.micronaut.client;

import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
@Named("pokeApiClient")
@Client("pokeapi")
@Header(name = "User-Agent", value = "${app.pokeapi.headers.user-agent}")
@Header(name = "Accept", value = "${app.pokeapi.headers.accept}")
public interface PokeApiClient extends PokemonClient {

    @Get("/pokemon/{nameOrId}")
    Mono<Pokemon> getPokemon(@PathVariable String nameOrId);

    @Get("/pokemon")
    Mono<PokemonListResponse> getPokemonList(
            @QueryValue(defaultValue = "20") Integer limit,
            @QueryValue(defaultValue = "0") Integer offset
    );
}

