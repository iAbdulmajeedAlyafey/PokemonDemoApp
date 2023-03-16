package com.example.demoapp.util

import com.example.demoapp.ui.common.state.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<UiState<T>>.postLoading() {
  value = UiState.Loading()
}

suspend fun <T> MutableSharedFlow<UiState<T>>.postLoading() {
 emit(UiState.Loading())
}

suspend fun <T> Channel<UiState<T>>.postLoading() {
  send(UiState.Loading())
}

