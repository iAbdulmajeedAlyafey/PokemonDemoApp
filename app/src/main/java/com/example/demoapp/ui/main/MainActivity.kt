package com.example.demoapp.ui.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.R
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.ui.common.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onBindView() = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() = binding?.apply {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.pokemon_search_fragment,
                R.id.pokemon_favorites_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}