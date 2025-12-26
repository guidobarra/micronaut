package com.guba.example.micronaut.web.controllers;

import com.guba.example.micronaut.client.dto.Pokemon;
import com.guba.example.micronaut.client.dto.PokemonListResponse;
import com.guba.example.micronaut.service.PokemonService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Controller("/api/pokemon")
@Tag(name = "Pokemon", description = "Pokemon API endpoints")
public class PokemonController {

    private static final Logger LOG = LoggerFactory.getLogger(PokemonController.class);
    private final PokemonService pokemonService;

    @Inject
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
        LOG.info("PokemonController initialized");
    }

    @Get(uri = "/{nameOrId}", produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get Pokemon by name or ID",
            description = "Retrieves detailed information about a specific Pokemon by its name or ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pokemon found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Pokemon.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pokemon not found"
    )
    public Mono<Pokemon> getPokemon(@PathVariable String nameOrId) {
        LOG.info("GET /api/pokemon/{}", nameOrId);
        return pokemonService.getPokemon(nameOrId)
                .onErrorResume(error -> {
                    LOG.error("Error retrieving Pokemon: {}", nameOrId, error);
                    return Mono.error(error);
                });
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get Pokemon list",
            description = "Retrieves a paginated list of Pokemon"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pokemon list retrieved successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PokemonListResponse.class))
    )
    public Mono<PokemonListResponse> getPokemonList(
            @QueryValue(defaultValue = "20") Integer limit,
            @QueryValue(defaultValue = "0") Integer offset
    ) {
        LOG.info("GET /api/pokemon?limit={}&offset={}", limit, offset);
        return pokemonService.getPokemonList(limit, offset)
                .onErrorResume(error -> {
                    LOG.error("Error retrieving Pokemon list", error);
                    return Mono.error(error);
                });
    }
}

