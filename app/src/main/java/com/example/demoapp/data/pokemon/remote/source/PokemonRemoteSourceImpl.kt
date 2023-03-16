package com.example.demoapp.data.pokemon.remote.source

import com.example.demoapp.data.pokemon.remote.api.PokemonApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRemoteSourceImpl @Inject constructor(
    private val apiService: PokemonApiService,
) : PokemonRemoteSource {

    override fun getPokemonList(healthPoints: Int) = flow {
        emit(apiService.getPokemonList("hp:$healthPoints"))
    }
}