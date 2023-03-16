package com.example.demoapp.ui.search

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.util.postLoading
import com.example.demoapp.util.toUiState
import com.example.demoapp.util.uiStateFlowOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _uiState = uiStateFlowOf<List<Pokemon>>()
    val uiState = _uiState.asStateFlow()

    var searchQuery: String = ""
        set(value) {
            field = value
            getPokemonList(value)
        }

    fun getPokemonList(query: String) = flowOf(query)
        .filter { it.length > 1 && it.isDigitsOnly()  }
        .onEmpty { _uiState.value = UiState.Empty() }
        .map(String::toInt)
        .onStart { _uiState.postLoading() }
        .flatMapLatest { pokemonRepository.getPokemonList(it) }
        .onEach { _uiState.value = it.toUiState() }
        .catch { _uiState.value = it.toUiState() }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    fun onClickPokemon(pokemon: Pokemon) {
        // todo navigate...
    }
}