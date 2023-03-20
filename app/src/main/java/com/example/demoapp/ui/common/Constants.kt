package com.example.demoapp.ui.common

import kotlinx.coroutines.flow.SharingStarted

/**
 * Keeps the upstream flow active for 5 seconds after the disappearance of the last collector.
 *
 * This avoids restarting the upstream flow in some situations such as configuration changes.
 */
val SHARING_STRATEGY = SharingStarted.WhileSubscribed(5_000)

object FragmentResult {
    const val UPDATE_POKEMON_SEARCH_LIST = "update_pokemon_search_list"
}
