package com.example.demoapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonDetailsBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.FragmentResult
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

    override val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonDetailsBinding.inflate(inflater)

    override fun observeUi() = collectFlow {
        launch { viewModel.uiState.collect(::handleUiState) }
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

    override fun onBackButtonPressed() {
        // Set a fragment result to refresh pokemon search screen when user re-opens it.
        // As it is required in the task.
        setFragmentResult(FragmentResult.UPDATE_POKEMON_SEARCH_LIST, bundleOf())
        super.onBackButtonPressed()
    }
}