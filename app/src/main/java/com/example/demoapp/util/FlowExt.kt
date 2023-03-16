package com.example.demoapp.util

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.demoapp.R
import com.example.demoapp.ui.common.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun Fragment.collectFlow(
  state: Lifecycle.State = Lifecycle.State.STARTED,
  block: suspend CoroutineScope.() -> Unit,
) {
  viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(state) {
      block()
    }
  }
}

fun AppCompatActivity.collectFlow(
  state: Lifecycle.State = Lifecycle.State.STARTED,
  block: suspend CoroutineScope.() -> Unit,
) {
  lifecycleScope.launch {
    repeatOnLifecycle(state) {
      block()
    }
  }
}

fun <T> uiStateFlowOf() = MutableStateFlow<UiState<T>>(UiState.Init())

val <T> MutableStateFlow<T>.isLoading: Boolean get() = value is UiState.Loading<*>

val <T> MutableStateFlow<T>.isSuccess: Boolean get() = value is UiState.Success<*>

fun <T> MutableStateFlow<UiState<T>>.successData(): T? {
  val value = this.value
  return if (value is UiState.Success<*>) {
    value.data as? T
  } else
    null
}

fun <T> T.toUiState(
  @StringRes emptyListMessage: Int = R.string.common_no_result_found,
): UiState<T> = when {
  this is List<*> && this.isEmpty() -> UiState.Empty(emptyListMessage)
  else -> UiState.Success(this)
}

fun <T> Throwable.toUiState(): UiState<T> = UiState.Error("Error")