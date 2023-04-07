package com.example.demoapp.ui.pokemon.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonFavoritesBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.ui.pokemon.favorite.PokemonFavoritesViewModel.PokemonFavoritesEvent
import com.example.demoapp.ui.pokemon.favorite.PokemonFavoritesViewModel.PokemonFavoritesEvent.OpenPokemonDetailsView
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.navigateSafely
import com.example.demoapp.util.showError
import com.example.demoapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonFavoritesFragment : BaseVMFragment<FragmentPokemonFavoritesBinding>(),
    PokemonFavoritesAdapter.PokemonClickListener {

    override val viewModel by hiltNavGraphViewModels<PokemonFavoritesViewModel>(R.id.nav_main)

    private lateinit var pokemonAdapter: PokemonFavoritesAdapter

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonFavoritesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritePokemonList()
    }

    override fun setupViews() = binding?.apply {
        pokemonAdapter = PokemonFavoritesAdapter(this@PokemonFavoritesFragment)
        rcvPokemon.adapter = pokemonAdapter
    }

    override fun observeUi() = collectFlow {
        with(viewModel) {
            launch { uiState.collect(::handleUiState) }
            launch { uiEvent.collect(::handleUiEvent) }
            launch { deletePokemonEventState.collect(::handleDeletePokemonEventState) }
        }
    }

    private fun handleUiState(state: UiState<List<Pokemon>>) = binding?.apply {
        rcvPokemon.isVisible = state is Success
        tvEmpty.isVisible = state is Empty
        when (state) {
            is Success -> pokemonAdapter.submitList(state.data)
            is Error -> showError(state.error)
            else -> Unit
        }
    }

    private fun handleUiEvent(event: PokemonFavoritesEvent) = when (event) {
        is OpenPokemonDetailsView -> navController.navigateSafely(
            PokemonFavoritesFragmentDirections.actionToPokemonDetailsFragment(event.id)
        )
    }

    private fun handleDeletePokemonEventState(event: UiState<Pokemon>) = when (event) {
        is Success -> {
            val message = getString(R.string.pokemon_favorites_deleted_, event.data.name)
            toast(message)
        }
        is Error -> showError(event.error)
        else -> Unit
    }

    override fun onClickPokemon(pokemonId: String) = viewModel.onClickPokemon(pokemonId)

    override fun onLongClickPokemon(pokemon: Pokemon) = viewModel.onLongClickPokemon(pokemon)
}