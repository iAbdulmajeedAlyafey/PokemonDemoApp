package com.example.demoapp.testdoubles

import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import com.example.demoapp.data.pokemon.local.source.PokemonLocalSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TestPokemonLocalSourceImpl @Inject constructor() : PokemonLocalSource {

    private val favoritesListFlow: MutableSharedFlow<List<CachedPokemon>> =
        MutableSharedFlow(replay = 1)

    override fun getFavoritePokemonList() = favoritesListFlow

    override suspend fun saveFavoritePokemon(pokemon: CachedPokemon) {
        val list = favoritesListFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        if (list.contains(pokemon)) return
        list.add(pokemon)
        favoritesListFlow.emit(list)
    }

    override suspend fun deleteFavoritePokemon(pokemon: CachedPokemon) {
        val list = favoritesListFlow.replayCache.firstOrNull().orEmpty().toMutableList()
        list.remove(pokemon)
        favoritesListFlow.emit(list)
    }

    override suspend fun saveLastPokemonDetailsEncrypted(pokemon: CachedPokemon) = Unit

    override fun getLastEncryptedPokemonDetails() = flowOf("")
}