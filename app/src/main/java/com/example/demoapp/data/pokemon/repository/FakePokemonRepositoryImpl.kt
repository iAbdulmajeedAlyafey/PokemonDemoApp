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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Fake implementation of the [PokemonRepository] that returns hardcoded pokemon data.
 *
 * This helps in running the app without an internet connection or backend integration.
 */
class FakePokemonRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PokemonRepository {

    private val cachedFavoriteFlow: MutableSharedFlow<List<Pokemon>> = MutableSharedFlow(replay = 1)

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

    override fun getFavoritePokemonList(): Flow<List<Pokemon>> = cachedFavoriteFlow

    override suspend fun saveFavoritePokemon(pokemon: Pokemon) {
        val list = cachedFavoriteFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        if (list.contains(pokemon)) return
        list.add(pokemon)
        cachedFavoriteFlow.emit(list)
    }

    override suspend fun deleteFavoritePokemon(pokemon: Pokemon) {
        val list = cachedFavoriteFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        list.remove(pokemon)
        cachedFavoriteFlow.emit(list)
    }

    override suspend fun saveLastPokemonDetailsEncrypted(pokemon: Pokemon) =
        throw NotImplementedError()

    override fun getLastEncryptedPokemonDetails() =
        throw NotImplementedError()
}