package com.example.demoapp.data.pokemon.remote.model

import com.google.gson.annotations.SerializedName

data class ApiPokemonSearchResponse(
    @SerializedName("data") val data: List<ApiPokemon>?
)

data class ApiPokemon(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("hp") val healthPoints: Int?,
    @SerializedName("images") val image: ApiPokemonImage?,
)

data class ApiPokemonImage(
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?,
)