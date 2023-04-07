package com.example.demoapp.ui.pokemon.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.SHARING_STRATEGY
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.util.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val args = PokemonDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val uiState: StateFlow<UiState<Pokemon>> =
        pokemonUiState(
            pokemonId = args.pokemonId,
            repository = pokemonRepository
        )
            .printEncryptedPokemonDetails()
            .map { it.first.toUiState() } // it.first = Pokemon
            .onStart { emit(UiState.Loading()) }
            .catch { emit(it.toUiState()) }
            .flowOn(ioDispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SHARING_STRATEGY,
                initialValue = UiState.Init(),
            )

    private fun pokemonUiState(
        pokemonId: String,
        repository: PokemonRepository
    ): Flow<Pair<Pokemon, String>> = combine(
        repository.getPokemonDetails(pokemonId),
        repository.getLastEncryptedPokemonDetails(),
        ::Pair
    )

    private fun Flow<Pair<Pokemon, String>>.printEncryptedPokemonDetails() = onEach {
        val (_, encryptedDetails) = it
        Timber.e("Encrypted Pokemon is: $encryptedDetails")
    }
}