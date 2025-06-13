package com.example.demoapp.ui.details

import androidx.lifecycle.SavedStateHandle
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.testdoubles.TestPokemonRepositoryImpl
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.pokemon.details.PokemonDetailsViewModel
import com.example.demoapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Subject Under Test.
    private lateinit var viewModel: PokemonDetailsViewModel

    private val pokemonRepository = TestPokemonRepositoryImpl()

    @Before
    fun beforeTest() {
        viewModel = PokemonDetailsViewModel(
            pokemonRepository = pokemonRepository,
            savedStateHandle = SavedStateHandle(mapOf("pokemonId" to "TestId")),
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun uiState_whenInitialized_thenFirstStateIsLoading() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }
        assertThat(viewModel.uiState.value).isInstanceOf(UiState.Loading::class.java)
        job.cancel()
    }

    @Test
    fun uiState_WhenGetPokemonDetails_thenStateIsSuccess() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }
        pokemonRepository.setPokemonDetails(testInputPokemon)

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(UiState.Success::class.java)

        job.cancel()
    }

    @Test
    fun uiState_WhenGetPokemonDetails_thenDataIsEqualsToRepositoryData() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }

        val repositoryPokemon = testInputPokemon

        pokemonRepository.setPokemonDetails(repositoryPokemon)

        val state = viewModel.uiState.value
        val pokemon = (state as UiState.Success).data

        assertThat(pokemon).isEqualTo(repositoryPokemon)

        job.cancel()
    }

    @Test
    fun uiState_WhenGetPokemonDetailsFailed_thenStateIsError() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }
        // Make the repository returns exception.
        pokemonRepository.setPokemonDetails(null)

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(UiState.Error::class.java)

        job.cancel()
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