package com.example.demoapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentPokemonSearchBinding
import com.example.demoapp.ui.common.base.fragment.BaseVMFragment
import com.example.demoapp.util.collectFlow
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

    override fun setOnClickListeners() = binding
}