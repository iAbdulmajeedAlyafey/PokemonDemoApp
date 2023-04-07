package com.example.demoapp.ui.pokemon.favorite

import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.util.toUiState
import com.example.demoapp.util.uiStateFlowOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonFavoritesViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _uiState = uiStateFlowOf<List<Pokemon>>()
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<PokemonFavoritesEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _deletePokemonEventState = Channel<UiState<Pokemon>>()
    val deletePokemonEventState = _deletePokemonEventState.receiveAsFlow()

    fun getFavoritePokemonList() = pokemonRepository.getFavoritePokemonList()
        .onEach { _uiState.value = it.toUiState() }
        .catch { _uiState.value = it.toUiState() }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    fun onLongClickPokemon(pokemon: Pokemon) {
        flowOf(pokemon)
            .onEach { pokemonRepository.deleteFavoritePokemon(it) }
            .onEach { _deletePokemonEventState.send(it.toUiState()) }
            .catch { _deletePokemonEventState.send(it.toUiState()) }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    fun onClickPokemon(pokemonId: String) {
        PokemonFavoritesEvent.OpenPokemonDetailsView(pokemonId).emit()
    }

    private fun PokemonFavoritesEvent.emit() = viewModelScope.launch {
        _uiEvent.send(this@emit)
    }

    sealed class PokemonFavoritesEvent {
        data class OpenPokemonDetailsView(val id: String) : PokemonFavoritesEvent()
    }
}