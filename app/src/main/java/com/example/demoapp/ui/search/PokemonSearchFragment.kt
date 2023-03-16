package com.example.demoapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonSearchBinding
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.ui.common.state.UiState
import com.example.demoapp.ui.common.state.UiState.*
import com.example.demoapp.util.collectFlow
import com.example.demoapp.util.showError
import com.example.demoapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonSearchFragment : BaseVMFragment<FragmentPokemonSearchBinding>() {

    override val viewModel by hiltNavGraphViewModels<PokemonSearchViewModel>(R.id.nav_main)

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonSearchBinding.inflate(inflater)

    override fun setupViews() = binding

    override fun observeUi() = collectFlow {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonList(10)
    }

    private fun handleEventState(state: UiState<Unit>) {
        setLoading(state is Loading)
        when(state) {
            is Success -> toast(getString(R.string.pokemon_search_saved))
            is Error -> showError(state.error)
            else -> Unit
        }
    }
    override fun setOnClickListeners() = binding
}