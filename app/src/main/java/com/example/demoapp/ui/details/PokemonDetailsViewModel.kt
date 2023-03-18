package com.example.demoapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.di.MockRepository
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.util.postLoading
import com.example.demoapp.util.toUiState
import com.example.demoapp.util.uiStateFlowOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    @MockRepository private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _uiState = uiStateFlowOf<Pokemon>()
    val uiState = _uiState.asStateFlow()

    private val args = PokemonDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getPokemonDetails(args.pokemonId)
    }

    private fun getPokemonDetails(id: String) = pokemonRepository.getPokemonDetails(id)
        .onStart { _uiState.postLoading() }
        .onEach { _uiState.value = it.toUiState() }
        .catch { _uiState.value = it.toUiState() }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)
}