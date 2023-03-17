package com.example.demoapp.data.pokemon.repository

import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MockPokemonRepositoryImpl @Inject constructor() : PokemonRepository {

    override fun getPokemonList(healthPoints: Int) =
        throw NotImplementedError()

    override fun getFavoritePokemonList(): Flow<List<Pokemon>> =
        throw NotImplementedError()

    override fun getPokemonDetails(id: String): Flow<Pokemon> =
        throw NotImplementedError()

    override suspend fun deleteFavoritePokemon(pokemon: Pokemon) =
        throw NotImplementedError()

    override suspend fun saveFavoritePokemon(pokemon: Pokemon) =
        throw NotImplementedError()
}