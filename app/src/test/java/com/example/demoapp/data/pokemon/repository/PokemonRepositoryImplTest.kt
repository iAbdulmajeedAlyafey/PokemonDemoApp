package com.example.demoapp.data.pokemon.repository

import com.example.demoapp.data.pokemon.local.source.PokemonLocalSource
import com.example.demoapp.data.pokemon.mapper.asCachedPokemon
import com.example.demoapp.data.pokemon.mapper.asPokemonList
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
    fun pokemonRepository_whenFetchingPokemonList_thenPokemonAreRetrievedFromRemote() = runTest {
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
    fun pokemonRepository_whenSavingFavoritePokemon_thenPokemonIsRetrievedFromCache() = runTest {
        val pokemon = testInputPokemon.asCachedPokemon()

        localSource.saveFavoritePokemon(pokemon)

        val savedPokemon = pokemonRepository
            .getFavoritePokemonList()
            .first()
            .first()

        assertThat(savedPokemon).isEqualTo(testInputPokemon)
    }

    @Test
    fun pokemonRepository_whenDeletingLastFavoritePokemon_thenRetrievedListFromCacheIsEmpty() =
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
}

private const val POKEMON_NAME = "Test Pokemon"
private const val POKEMON_ARTIST = "Test ARTIST"
private const val POKEMON_HEALTH_POINT = 10
private const val POKEMON_IMAGE = ""
private const val POKEMON_IS_FAVORITE = true

private val testInputPokemon = Pokemon(
    id = "10",
    name = POKEMON_NAME,
    artist = POKEMON_ARTIST,
    healthPoints = POKEMON_HEALTH_POINT,
    image = POKEMON_IMAGE,
    isFavorite = POKEMON_IS_FAVORITE
)