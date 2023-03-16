package com.example.demoapp.util

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.hide() = apply {
  visibility = View.GONE
}

fun View.invisible() = apply {
  visibility = View.INVISIBLE
}

fun View.show() = apply {
  visibility = View.VISIBLE
}

fun View.isNotVisible() = visibility != View.VISIBLE

fun View.onClick(body: (View) -> Unit) = setOnClickListener(SingleClickListener { body(it) })

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(this.context)

/**
 * This type of [View.OnClickListener] prevents multiple view clicks at one time.
 */
class SingleClickListener(
  private var defaultInterval: Int = 1000,
  private val onSingleClick: (View) -> Unit,
) : View.OnClickListener {
  private var lastTimeClicked: Long = 0
  override fun onClick(v: View) {
    if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
      return
    }
    lastTimeClicked = SystemClock.elapsedRealtime()
    onSingleClick(v)
  }
}