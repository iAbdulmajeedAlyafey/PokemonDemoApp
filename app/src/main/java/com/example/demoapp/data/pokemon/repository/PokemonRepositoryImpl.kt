package com.example.demoapp.data.pokemon.repository

import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import com.example.demoapp.data.pokemon.local.source.PokemonLocalSource
import com.example.demoapp.data.pokemon.mapper.asCachedPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemonList
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import com.example.demoapp.data.pokemon.remote.source.PokemonRemoteSource
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteSource: PokemonRemoteSource,
    private val localSource: PokemonLocalSource,
) : PokemonRepository {

    override fun getPokemonList(healthPoints: Int) =
        remoteSource.getPokemonList(healthPoints)
            .map(ApiPokemonSearchResponse::asPokemonList)

    override fun getFavoritePokemonList() =
        localSource.getFavoritePokemonList()
            .map(List<CachedPokemon>::asPokemonList)

    override fun getPokemonDetails(id: String) =
        remoteSource.getPokemonDetails(id)
            .map(ApiPokemonDetailsResponse::asPokemon)

    override suspend fun saveFavoritePokemon(pokemon: Pokemon) =
        localSource.saveFavoritePokemon(pokemon.asCachedPokemon())

    override suspend fun deleteFavoritePokemon(pokemon: Pokemon) =
        localSource.deleteFavoritePokemon(pokemon.asCachedPokemon())
}