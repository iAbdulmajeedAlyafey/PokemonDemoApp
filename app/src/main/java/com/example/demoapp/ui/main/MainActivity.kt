package com.example.demoapp.ui.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun setupViews() = binding?.apply {
        appBarConfiguration = AppBarConfiguration(topLevelFragments)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(navController, appBarConfiguration)
}