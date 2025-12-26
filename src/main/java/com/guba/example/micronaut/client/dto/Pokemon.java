package com.guba.example.micronaut.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pokemon(
        Integer id,
        String name,
        Integer height,
        Integer weight,
        Integer baseExperience,
        List<PokemonAbility> abilities,
        List<PokemonType> types,
        PokemonSprites sprites,
        List<PokemonStat> stats
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PokemonAbility(
            @JsonProperty("is_hidden") Boolean isHidden,
            Integer slot,
            Ability ability
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Ability(
                String name,
                String url
        ) {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PokemonType(
            Integer slot,
            Type type
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Type(
                String name,
                String url
        ) {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PokemonSprites(
            @JsonProperty("front_default") String frontDefault,
            @JsonProperty("back_default") String backDefault,
            @JsonProperty("front_shiny") String frontShiny,
            @JsonProperty("back_shiny") String backShiny
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PokemonStat(
            Integer effort,
            @JsonProperty("base_stat") Integer baseStat,
            Stat stat
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Stat(
                String name,
                String url
        ) {}
    }
}

