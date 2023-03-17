package com.example.demoapp.data.pokemon.remote.source

import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRemoteSource {

    fun getPokemonList(healthPoints: Int): Flow<ApiPokemonSearchResponse>

    fun getPokemonDetails(id: String): Flow<ApiPokemonDetailsResponse>
}