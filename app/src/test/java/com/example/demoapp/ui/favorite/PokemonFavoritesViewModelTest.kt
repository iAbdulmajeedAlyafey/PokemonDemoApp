package com.example.demoapp.ui.favorite

import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.testdoubles.TestPokemonRepositoryImpl
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonFavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Subject Under Test.
    private lateinit var viewModel: PokemonFavoritesViewModel

    private val uiStatesList: MutableList<UiState<List<Pokemon>>> = mutableListOf()

    private val pokemonRepository = TestPokemonRepositoryImpl()

    @Before
    fun beforeTest() {
        viewModel = PokemonFavoritesViewModel(
            pokemonRepository = pokemonRepository,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @After
    fun afterTest() {
        uiStatesList.clear()
    }

    @Test
    fun uiState_whenInitialized_thenStateIsInit() = runTest {
        assertThat(viewModel.uiState.value).isInstanceOf(UiState.Init::class.java)
    }

    @Test
    fun uiState_whenGetFavoritePokemonList_thenStateIsSuccess() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }
        assertThat(uiStatesList.first()).isInstanceOf(UiState.Init::class.java)

        pokemonRepository.setFavoritePokemonList(testInputPokemonList)

        viewModel.getFavoritePokemonList()

        assertThat(uiStatesList.last()).isInstanceOf(UiState.Success::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenGetFavoritePokemonListIsEmpty_thenStateIsEmpty() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }
        pokemonRepository.setFavoritePokemonList(emptyList())

        viewModel.getFavoritePokemonList()

        assertThat(uiStatesList.last()).isInstanceOf(UiState.Empty::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenDeletingPokemonFromList_thenStateIsSuccess() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }

        pokemonRepository.setFavoritePokemonList(testInputPokemonList)

        viewModel.getFavoritePokemonList()

        pokemonRepository.deleteFavoritePokemon(testInputPokemonList.first())

        assertThat(uiStatesList.last()).isInstanceOf(UiState.Success::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenDeletingLastSavedPokemon_thenStateIsEmpty() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }
        val pokemon = testInputPokemon
        pokemonRepository.saveFavoritePokemon(pokemon)

        viewModel.getFavoritePokemonList()

        pokemonRepository.deleteFavoritePokemon(pokemon)

        assertThat(uiStatesList.last()).isInstanceOf(UiState.Empty::class.java)

        job.cancel()
    }
}

private const val POKEMON_NAME = "Test Pokemon"
private const val POKEMON_ARTIST = "Test ARTIST"
private const val POKEMON_HEALTH_POINT = 10
private const val POKEMON_IMAGE = ""
private const val POKEMON_IS_FAVORITE = true

private val testInputPokemonList = listOf(
    Pokemon(
        id = "1",
        name = POKEMON_NAME,
        artist = POKEMON_ARTIST,
        healthPoints = POKEMON_HEALTH_POINT,
        image = POKEMON_IMAGE,
        isFavorite = POKEMON_IS_FAVORITE
    ),
    Pokemon(
        id = "2",
        name = POKEMON_NAME,
        artist = POKEMON_ARTIST,
        healthPoints = POKEMON_HEALTH_POINT,
        image = POKEMON_IMAGE,
        isFavorite = POKEMON_IS_FAVORITE
    ),
    Pokemon(
        id = "3",
        name = POKEMON_NAME,
        artist = POKEMON_ARTIST,
        healthPoints = POKEMON_HEALTH_POINT,
        image = POKEMON_IMAGE,
        isFavorite = POKEMON_IS_FAVORITE
    )
)

private val testInputPokemon = Pokemon(
    id = "10",
    name = POKEMON_NAME,
    artist = POKEMON_ARTIST,
    healthPoints = POKEMON_HEALTH_POINT,
    image = POKEMON_IMAGE,
    isFavorite = POKEMON_IS_FAVORITE
)