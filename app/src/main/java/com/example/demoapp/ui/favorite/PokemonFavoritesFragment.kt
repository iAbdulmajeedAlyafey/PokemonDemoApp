package com.example.demoapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonFavoritesBinding
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.util.collectFlow

class PokemonFavoritesFragment : BaseVMFragment<FragmentPokemonFavoritesBinding>() {

    override val viewModel by hiltNavGraphViewModels<PokemonFavoritesViewModel>(R.id.nav_main)

    override fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonFavoritesBinding.inflate(inflater)

    override fun setupViews() = binding

    override fun observeUi() = collectFlow {}

    override fun setOnClickListeners() = binding
}