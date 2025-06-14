package com.example.demoapp.ui.pokemon.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonSearchBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.FragmentResult
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.delegates.progressbar.ProgressBar
import com.example.demoapp.ui.common.delegates.progressbar.ProgressBarImpl
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.ui.pokemon.search.PokemonSearchViewModel.PokemonSearchEvent
import com.example.demoapp.ui.pokemon.search.PokemonSearchViewModel.PokemonSearchEvent.OpenPokemonDetailsView
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.navigateSafely
import com.example.demoapp.util.onQueryChanges
import com.example.demoapp.util.query
import com.example.demoapp.util.showError
import com.example.demoapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonSearchFragment : BaseVMFragment<FragmentPokemonSearchBinding>(),
    ProgressBar by ProgressBarImpl(),
    PokemonSearchAdapter.PokemonClickListener {

    override val viewModel by hiltNavGraphViewModels<PokemonSearchViewModel>(R.id.nav_main)

    private lateinit var pokemonAdapter: PokemonSearchAdapter

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonSearchBinding.inflate(inflater)

    override fun setupViews() = binding?.apply {
        setUpProgressBar(this@PokemonSearchFragment)
        pokemonAdapter = PokemonSearchAdapter(this@PokemonSearchFragment)
        rcvPokemon.adapter = pokemonAdapter
    }

    override fun observeUi() = collectFlow {
        launch { viewModel.uiState.collect(::handleUiState) }
        launch { viewModel.uiEvent.collect(::handleUiEvent) }
        launch { viewModel.savePokemonEventState.collect(::handleSavePokemonEventState) }
    }

    private fun handleUiState(state: UiState<List<Pokemon>>) = binding?.apply {
        setLoading(state is Loading)
        rcvPokemon.isVisible = state is Success
        tvEmpty.isVisible = state is Init || state is Empty
        when (state) {
            is Success -> pokemonAdapter.submitList(state.data)
            is Error -> showError(state.error)
            else -> Unit
        }
    }

    private fun handleUiEvent(event: PokemonSearchEvent) = when (event) {
        is OpenPokemonDetailsView -> navController.navigateSafely(
            PokemonSearchFragmentDirections.actionToPokemonDetailsFragment(event.id)
        )
    }

    private fun handleSavePokemonEventState(event: UiState<Pokemon>) = when (event) {
        is Success -> toast(getString(R.string.pokemon_search_saved))
        is Error -> showError(event.error)
        else -> Unit
    }

    override fun setOnTextChangeListeners() = binding?.apply {
        svPokemon.onQueryChanges(
            lifecycleCoroutineScope = lifecycleScope,
            minLength = 2,
            doSearch = { viewModel.searchQuery = it }
        )
    }

    override fun checkFragmentResultListener() {
        setFragmentResultListener(FragmentResult.UPDATE_POKEMON_SEARCH_LIST) { _, _ ->
            toast(getString(R.string.pokemon_search_refresh_msg))
            viewModel.searchQuery = binding?.svPokemon.query()
        }
    }

    override fun onClickPokemon(pokemonId: String) = viewModel.onClickPokemon(pokemonId)

    override fun onClickSavePokemon(pokemon: Pokemon) = viewModel.onClickSavePokemon(pokemon)
}