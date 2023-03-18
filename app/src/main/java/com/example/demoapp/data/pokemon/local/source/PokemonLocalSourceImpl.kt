package com.example.demoapp.data.pokemon.local.source

import com.example.demoapp.data.common.source.local.SecureDataStore
import com.example.demoapp.data.pokemon.local.dao.PokemonDao
import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import javax.inject.Inject

class PokemonLocalSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val dataStore: SecureDataStore
) : PokemonLocalSource {

    override fun getFavoritePokemonList() =
        pokemonDao.getFavoritePokemonList()

    override suspend fun saveFavoritePokemon(pokemon: CachedPokemon) =
        pokemonDao.insertFavoritePokemon(pokemon)

    override suspend fun deleteFavoritePokemon(pokemon: CachedPokemon) =
        pokemonDao.deleteFavoritePokemon(pokemon)

    override suspend fun saveLastPokemonDetailsEncrypted(pokemon: CachedPokemon) =
        dataStore.savePokemonInfo(pokemon)

    override fun getLastEncryptedPokemonDetails() =
        dataStore.pokemonDetailsEncrypted
}