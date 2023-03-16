package com.example.demoapp.data.pokemon.mapper

import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import com.example.demoapp.data.pokemon.remote.model.ApiSearchResponse
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.util.orDefault

fun List<CachedPokemon>.asPokemonList() = map {
    Pokemon(
        id = it.id,
        name = it.name,
        artist = it.artist,
        healthPoints = it.healthPoints,
        image = it.image,
        isFavorite = it.isFavorite,
    )
}

fun ApiSearchResponse.asPokemonList() = data?.map {
    Pokemon(
        id = it.id.orEmpty(),
        name = it.name.orEmpty(),
        artist = it.artist.orEmpty(),
        healthPoints = it.healthPoints.orDefault(),
        image = if (it.image?.large == null) it.image?.small.orEmpty() else it.image.large,
        isFavorite = false
    )
}.orEmpty()

fun Pokemon.asCachedPokemon() = CachedPokemon(
    id = id,
    name = name,
    artist = artist,
    healthPoints = healthPoints,
    image = image,
    isFavorite = isFavorite
)