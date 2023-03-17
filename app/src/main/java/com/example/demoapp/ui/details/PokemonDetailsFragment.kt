package com.example.demoapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonDetailsBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.load
import com.example.demoapp.util.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailsFragment : BaseVMFragment<FragmentPokemonDetailsBinding>() {

    override val viewModel by hiltNavGraphViewModels<PokemonDetailsViewModel>(R.id.nav_main)

    private val args: PokemonDetailsFragmentArgs by navArgs()

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonDetailsBinding.inflate(inflater)

    override fun observeUi() = collectFlow {
        launch { viewModel.uiState.collect(::handleUiState) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonDetails(args.pokemonId)
    }

    private fun handleUiState(state: UiState<Pokemon>) {
        setLoading(state is Loading)
        when (state) {
            is Success -> showPokemonDetails(state.data)
            is Error -> showError(state.error)
            else -> Unit
        }
    }

    private fun showPokemonDetails(pokemon: Pokemon) = binding?.apply {
        ivIcon.load(pokemon.image)
        tvName.text = pokemon.name
        tvArtist.text = getString(R.string.pokemon_details_artist_, pokemon.artist)
    }
}