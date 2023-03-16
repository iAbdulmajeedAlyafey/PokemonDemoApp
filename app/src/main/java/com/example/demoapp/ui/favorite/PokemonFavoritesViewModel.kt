package com.example.demoapp.ui.favorite

import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.util.toUiState
import com.example.demoapp.util.uiStateFlowOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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

    fun getFavoritePokemonList() = pokemonRepository.getFavoritePokemonList()
        .onEach { _uiState.value = it.toUiState() }
        .catch { _uiState.value = it.toUiState() }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

    fun deleteFavorite(pokemon: Pokemon) = viewModelScope.launch(ioDispatcher) {
        pokemonRepository.deleteFavoritePokemon(pokemon)
    }
}