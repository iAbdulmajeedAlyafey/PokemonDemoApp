package com.example.demoapp.data.pokemon.remote.source

import com.example.demoapp.data.pokemon.remote.model.ApiSearchResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRemoteSource {

    fun getPokemonList(healthPoints: Int): Flow<ApiSearchResponse>

}