package com.guba.example.micronaut.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonListResponse(
        Integer count,
        String next,
        String previous,
        List<PokemonListItem> results
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PokemonListItem(
            String name,
            String url
    ) {}
}

