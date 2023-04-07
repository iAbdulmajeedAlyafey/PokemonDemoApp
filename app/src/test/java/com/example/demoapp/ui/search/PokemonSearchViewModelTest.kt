package com.example.demoapp.ui.search

import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.testdoubles.TestPokemonRepositoryImpl
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.pokemon.search.PokemonSearchViewModel
import com.example.demoapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class PokemonSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Subject Under Test.
    private lateinit var viewModel: PokemonSearchViewModel

    private val uiStatesList: MutableList<UiState<List<Pokemon>>> = mutableListOf()

    private val pokemonRepository = TestPokemonRepositoryImpl()

    @Before
    fun beforeTest() {
        viewModel = PokemonSearchViewModel(
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
    fun uiState_whenQueryIsNotNumberOrBellowTwoDigits_thenStateIsEmpty() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }
        viewModel.searchQuery = "test..."

        val currentState = viewModel.uiState.value
        assertThat(currentState).isInstanceOf(UiState.Empty::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenQueryIsNumberAndLengthMoreThanOne_thenStateIsInitThenLoading() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }

        viewModel.searchQuery = "10"

        assertThat(uiStatesList[0]).isInstanceOf(UiState.Init::class.java)
        assertThat(uiStatesList[1]).isInstanceOf(UiState.Loading::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenQueryIsNumberAndLengthMoreThanOne_thenStateBecomesSuccess() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(uiStatesList)
        }
        pokemonRepository.savePokemonList(testInputPokemonList)

        viewModel.searchQuery = "10"

        assertThat(uiStatesList[0]).isInstanceOf(UiState.Init::class.java)
        assertThat(uiStatesList[1]).isInstanceOf(UiState.Loading::class.java)
        assertThat(uiStatesList[2]).isInstanceOf(UiState.Success::class.java)

        job.cancel()
    }

    @Test
    fun uiState_whenPokemonListIsEmptyAfterSearch_thenStateIsEmpty() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }

        pokemonRepository.savePokemonList(emptyList())

        viewModel.searchQuery = "100"

        val currentState = viewModel.uiState.value

        assertThat(currentState).isInstanceOf(UiState.Empty::class.java)

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