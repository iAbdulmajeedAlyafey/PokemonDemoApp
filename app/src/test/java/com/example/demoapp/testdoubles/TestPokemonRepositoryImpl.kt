package com.example.demoapp.testdoubles

import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class TestPokemonRepositoryImpl @Inject constructor() : PokemonRepository {

    private val pokemonListFlow: MutableSharedFlow<List<Pokemon>> = MutableSharedFlow(replay = 1)

    private val favoritesListFlow: MutableSharedFlow<List<Pokemon>> = MutableSharedFlow(replay = 1)

    private val detailsFlow: MutableSharedFlow<Pokemon> = MutableSharedFlow(replay = 1)

    override fun getPokemonList(healthPoints: Int) = pokemonListFlow

    override fun getPokemonDetails(id: String): Flow<Pokemon> = detailsFlow

    override fun getFavoritePokemonList(): Flow<List<Pokemon>> = favoritesListFlow

    override suspend fun saveFavoritePokemon(pokemon: Pokemon) {
        val list = favoritesListFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        if (list.contains(pokemon)) return
        list.add(pokemon)
        favoritesListFlow.emit(list)
    }

    override suspend fun deleteFavoritePokemon(pokemon: Pokemon) {
        val list = favoritesListFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        list.remove(pokemon)
        favoritesListFlow.emit(list)
    }

    override suspend fun saveLastPokemonDetailsEncrypted(pokemon: Pokemon) = Unit

    override fun getLastEncryptedPokemonDetails() = flowOf("")

    @TestOnly
    fun saveFavoritePokemonList(list: List<Pokemon>) {
        favoritesListFlow.tryEmit(list)
    }

    @TestOnly
    fun savePokemonList(list: List<Pokemon>) {
        pokemonListFlow.tryEmit(list)
    }
}