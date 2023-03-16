package com.example.demoapp.data.pokemon.remote.api

import com.example.demoapp.data.pokemon.remote.model.ApiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

  @GET("cards")
  suspend fun getPokemonList(
      @Query("q") healthPoints: String,
  ): ApiSearchResponse
}