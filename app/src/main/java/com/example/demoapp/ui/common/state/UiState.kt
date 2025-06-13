package com.example.demoapp.ui.common.state

import com.example.demoapp.R

sealed class UiState<T> {

  class Init<T> : UiState<T>()

  class Loading<T> : UiState<T>()

  data class Success<T>(val data: T) : UiState<T>()

  data class Error<T>(val error: String) : UiState<T>()

  class Empty<T>(val msgResId: Int = R.string.common_no_result_found) : UiState<T>()

}