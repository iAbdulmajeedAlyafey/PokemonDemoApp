package com.example.demoapp.data.pokemon.remote.model

import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.util.orDefault
import com.google.gson.annotations.SerializedName

data class ApiPokemon(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("hp") val healthPoints: Int?,
    @SerializedName("images") val image: ApiPokemonImage?,
)

data class ApiPokemonImage(
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?,
)

fun ApiPokemon.asPokemon() = Pokemon(
    id = id.orDefault(),
    name = name.orEmpty(),
    artist = artist.orEmpty(),
    healthPoints = healthPoints.orDefault(),
    image = if (image?.large == null) image?.small.orEmpty() else image.large,
    isFavorite = false
)