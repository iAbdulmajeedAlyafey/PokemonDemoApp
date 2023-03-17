package com.example.demoapp.data.pokemon.remote.api

import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("cards")
    suspend fun getPokemonList(@Query("q") healthPoints: String): ApiPokemonSearchResponse

    @GET("cards/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): ApiPokemonDetailsResponse
}