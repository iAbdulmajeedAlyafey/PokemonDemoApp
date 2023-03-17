package com.example.demoapp.data.pokemon.mapper

import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import com.example.demoapp.data.pokemon.remote.model.ApiPokemon
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.util.orDefault

fun CachedPokemon.asPokemon() = Pokemon(
    id = id,
    name = name,
    artist = artist,
    healthPoints = healthPoints,
    image = image,
    isFavorite = isFavorite,
)

fun Pokemon.asCachedPokemon() = CachedPokemon(
    id = id,
    name = name,
    artist = artist,
    healthPoints = healthPoints,
    image = image,
    isFavorite = isFavorite
)

fun ApiPokemon.asPokemon() = Pokemon(
    id = id.orEmpty(),
    name = name.orEmpty(),
    artist = artist.orEmpty(),
    healthPoints = healthPoints.orDefault(),
    image = if (image?.large == null) image?.small.orEmpty() else image.large,
    isFavorite = false
)

fun getDefaultPokemon() = Pokemon(
    id = "",
    name = "",
    artist = "",
    healthPoints = 0,
    image = "",
    isFavorite = true
)

fun List<CachedPokemon>.asPokemonList() = map(CachedPokemon::asPokemon)

fun ApiPokemonSearchResponse.asPokemonList() = data?.map(ApiPokemon::asPokemon).orEmpty()

fun ApiPokemonDetailsResponse.asPokemon() = data?.asPokemon() ?: getDefaultPokemon()