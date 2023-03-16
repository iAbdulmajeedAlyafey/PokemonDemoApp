package com.example.demoapp.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.util.toGson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ) : BaseViewModel() {

    fun getPokemonList(healthPoint: Int) {
        pokemonRepository.getPokemonList(healthPoint)
            .onEach { Log.d("TT", "Got the pokemons!! -> ${it.toGson()}") }
            .catch { it.printStackTrace() }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }
}