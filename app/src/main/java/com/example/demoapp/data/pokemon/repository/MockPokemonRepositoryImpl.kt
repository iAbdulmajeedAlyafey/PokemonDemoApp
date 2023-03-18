package com.example.demoapp.data.pokemon.repository

import android.content.Context
import com.example.demoapp.R
import com.example.demoapp.data.pokemon.mapper.asPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemonList
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.util.readFile
import com.example.demoapp.util.toObjectOrThrow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockPokemonRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PokemonRepository {

    private val cachedFavoriteList = mutableListOf<Pokemon>()

    override fun getPokemonList(healthPoints: Int) = flow {
        val json = context.readFile(R.raw.mock_pokemon_search_response)
        val response = json.toObjectOrThrow(ApiPokemonSearchResponse::class.java)
        emit(response.asPokemonList())
    }

    override fun getPokemonDetails(id: String): Flow<Pokemon> = flow {
        val json = context.readFile(R.raw.mock_pokemon_details_reponse)
        val response = json.toObjectOrThrow(ApiPokemonDetailsResponse::class.java)
        emit(response.asPokemon())
    }

    override fun getFavoritePokemonList(): Flow<List<Pokemon>> = flow {
        emit(cachedFavoriteList)
    }

    override suspend fun saveFavoritePokemon(pokemon: Pokemon) {
        cachedFavoriteList.add(pokemon)
    }

    override suspend fun deleteFavoritePokemon(pokemon: Pokemon) {
        cachedFavoriteList.remove(pokemon)
    }
}