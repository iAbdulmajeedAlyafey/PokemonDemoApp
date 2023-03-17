package com.example.demoapp.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSafely(
    navDirections: NavDirections,
    navOptions: NavOptions? = null,
) = runCatching { navigate(navDirections, navOptions) }