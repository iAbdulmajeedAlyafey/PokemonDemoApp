package com.example.demoapp.domain.pokemon.repository

import com.example.demoapp.domain.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(healthPoints: Int): Flow<List<Pokemon>>

    fun getFavoritePokemonList(): Flow<List<Pokemon>>

    fun getPokemonDetails(id: String): Flow<Pokemon>

    suspend fun saveFavoritePokemon(pokemon: Pokemon)

    suspend fun deleteFavoritePokemon(pokemon: Pokemon)

    suspend fun saveLastPokemonDetailsEncrypted(pokemon: Pokemon)

    fun getLastEncryptedPokemonDetails(): Flow<String>
}