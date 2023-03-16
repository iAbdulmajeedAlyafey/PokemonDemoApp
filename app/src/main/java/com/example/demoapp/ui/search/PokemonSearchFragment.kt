package com.example.demoapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonSearchBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.onQueryChanges
import com.example.demoapp.util.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonSearchFragment : BaseVMFragment<FragmentPokemonSearchBinding>() {

    override val viewModel by hiltNavGraphViewModels<PokemonSearchViewModel>(R.id.nav_main)

    private lateinit var pokemonAdapter: PokemonSearchAdapter

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonSearchBinding.inflate(inflater)

    override fun setupViews() = binding?.apply {
        rcvPokemon.apply {
            pokemonAdapter = PokemonSearchAdapter(onClickPokemon = { viewModel.onClickPokemon(it) })
            adapter = pokemonAdapter
        }
    }

    override fun setOnTextChangeListeners() = binding?.apply {
        svPokemon.onQueryChanges(
            lifecycleCoroutineScope = lifecycleScope,
            minLength = 2,
            doSearch = { viewModel.searchQuery = it }
        )
    }

    override fun observeUi() = collectFlow {
        viewModel.uiState.collect(::handleUiState)
    }

    private fun handleUiState(state: UiState<List<Pokemon>>) = binding?.apply {
        setLoading(state is Loading)
        rcvPokemon.isVisible = state is Success
        tvEmpty.isVisible = state is UiState.Init || state is Empty
        when (state) {
            is Success -> pokemonAdapter.submitList(state.data)
            is Error -> showError(state.error)
            else -> Unit
        }
    }

    override fun setOnClickListeners() = binding
}