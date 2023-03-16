package com.example.demoapp.data.pokemon.local.source

import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonLocalSource {

    fun getFavoritePokemonList(): Flow<List<CachedPokemon>>

    suspend fun saveFavoritePokemon(pokemon: CachedPokemon)

    suspend fun deleteFavoritePokemon(pokemon: CachedPokemon)
}
