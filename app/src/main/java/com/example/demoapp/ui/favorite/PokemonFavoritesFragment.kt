package com.example.demoapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonFavoritesBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.showError
import com.example.demoapp.util.toGson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFavoritesFragment : BaseVMFragment<FragmentPokemonFavoritesBinding>() {

    override val viewModel by hiltNavGraphViewModels<PokemonFavoritesViewModel>(R.id.nav_main)

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonFavoritesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritePokemonList()
    }

    override fun setupViews() = binding

    override fun observeUi() = collectFlow {
        viewModel.uiState.collect(::handleUiState)
    }

    private fun handleUiState(state: UiState<List<Pokemon>>) = binding?.apply {
        setLoading(state is Loading)
        when (state) {
            is Empty -> tvTitle.text = getString(state.msgResId)
            is Error -> showError(state.error)
            is Success -> tvTitle.text = state.data.toGson()
            else -> Unit
        }
    }

    override fun setOnClickListeners() = binding?.apply {

    }
}