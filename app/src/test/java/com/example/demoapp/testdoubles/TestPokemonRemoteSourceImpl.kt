package com.example.demoapp.testdoubles

import com.example.demoapp.data.pokemon.remote.model.ApiPokemon
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonImage
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import com.example.demoapp.data.pokemon.remote.source.PokemonRemoteSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TestPokemonRemoteSourceImpl @Inject constructor() : PokemonRemoteSource {

    override fun getPokemonList(healthPoints: Int) = flow {
        val response = ApiPokemonSearchResponse(data = testApiPokemonList)
        emit(response)
    }

    override fun getPokemonDetails(id: String) = flow {
        val response = ApiPokemonDetailsResponse(data = testApiPokemon)
        emit(response)
    }
}

private val testApiPokemonList = listOf(
    ApiPokemon(
        id = "1",
        name = "Test",
        artist = "Artist",
        healthPoints = 10,
        image = ApiPokemonImage(
            small = null,
            large = null
        ),
    ),
    ApiPokemon(
        id = "2",
        name = "Test",
        artist = "Artist",
        healthPoints = 10,
        image = ApiPokemonImage(
            small = null,
            large = null
        ),
    ),
    ApiPokemon(
        id = "3",
        name = "Test",
        artist = "Artist",
        healthPoints = 10,
        image = ApiPokemonImage(
            small = null,
            large = null
        ),
    )
)

private val testApiPokemon = ApiPokemon(
    id = "4",
    name = "Test",
    artist = "Artist",
    healthPoints = 10,
    image = ApiPokemonImage(
        small = null,
        large = null
    ),
)