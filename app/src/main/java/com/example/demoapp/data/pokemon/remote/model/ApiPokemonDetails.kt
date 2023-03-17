package com.example.demoapp.data.pokemon.remote.model

import com.google.gson.annotations.SerializedName

data class ApiPokemonDetailsResponse(
    @SerializedName("data") val data: ApiPokemon?
)