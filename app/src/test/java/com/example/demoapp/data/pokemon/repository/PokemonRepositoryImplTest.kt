package com.example.demoapp.data.pokemon.repository

import com.example.demoapp.data.pokemon.local.source.PokemonLocalSource
import com.example.demoapp.data.pokemon.mapper.asCachedPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemonList
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonDetailsResponse
import com.example.demoapp.data.pokemon.remote.model.ApiPokemonSearchResponse
import com.example.demoapp.data.pokemon.remote.source.PokemonRemoteSource
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.testdoubles.TestPokemonLocalSourceImpl
import com.example.demoapp.testdoubles.TestPokemonRemoteSourceImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class PokemonRepositoryImplTest {

    // Subject Under Test.
    private lateinit var pokemonRepository: PokemonRepository

    private lateinit var remoteSource: PokemonRemoteSource

    private lateinit var localSource: PokemonLocalSource

    @Before
    fun beforeTest() {
        remoteSource = TestPokemonRemoteSourceImpl()
        localSource = TestPokemonLocalSourceImpl()
        pokemonRepository = PokemonRepositoryImpl(
            remoteSource = remoteSource,
            localSource = localSource
        )
    }

    @Test
    fun pokemonRepository_whenGetPokemonList_thenPokemonAreRetrievedFromRemote() = runTest {
        val healthPoint = 10

        val remoteList =
            remoteSource.getPokemonList(healthPoint)
                .map(ApiPokemonSearchResponse::asPokemonList)
                .first()

        val repositoryList =
            pokemonRepository.getPokemonList(healthPoint)
                .first()

        assertThat(repositoryList).isEqualTo(remoteList)
    }

    @Test
    fun pokemonRepository_WhenGetFavoritePokemonList_thenPokemonAreRetrievedFromCache() = runTest {
        val cachedPokemon = testInputPokemon.asCachedPokemon()
        localSource.saveFavoritePokemon(cachedPokemon)

        val repositoryPokemon = pokemonRepository
            .getFavoritePokemonList()
            .first()
            .first()

        // Assert the previous saved pokemon = the pokemon we get from repository
        assertThat(repositoryPokemon).isEqualTo(cachedPokemon.asPokemon())
    }

    @Test
    fun pokemonRepository_whenDeleteLastFavoritePokemon_thenRetrievedListFromCacheIsEmpty() =
        runTest {
            val pokemon = testInputPokemon.asCachedPokemon()

            localSource.saveFavoritePokemon(pokemon)

            val savedPokemon = pokemonRepository
                .getFavoritePokemonList()
                .first()
                .first()

            assertThat(savedPokemon).isEqualTo(testInputPokemon)

            localSource.deleteFavoritePokemon(pokemon)

            val finalList = pokemonRepository.getFavoritePokemonList().first()
            assertThat(finalList).isEmpty()
        }

    @Test
    fun pokemonRepository_whenGetPokemonDetails_thenDetailsIsRetrievedFromRemote() =
        runTest {
            val id = POKEMON_ID

            val remotePokemon = remoteSource
                .getPokemonDetails(id)
                .map(ApiPokemonDetailsResponse::asPokemon)
                .first()

            val repositoryPokemon = pokemonRepository
                .getPokemonDetails(id)
                .first()

            assertThat(repositoryPokemon).isEqualTo(remotePokemon)
        }
}

private const val POKEMON_ID = "XXX-YYY"
private const val POKEMON_NAME = "Test Pokemon"
private const val POKEMON_ARTIST = "Test ARTIST"
private const val POKEMON_HEALTH_POINT = 10
private const val POKEMON_IMAGE = ""
private const val POKEMON_IS_FAVORITE = true

private val testInputPokemon = Pokemon(
    id = POKEMON_ID,
    name = POKEMON_NAME,
    artist = POKEMON_ARTIST,
    healthPoints = POKEMON_HEALTH_POINT,
    image = POKEMON_IMAGE,
    isFavorite = POKEMON_IS_FAVORITE
)