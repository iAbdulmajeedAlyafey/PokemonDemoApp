package com.example.demoapp.ui.common.view

import android.R
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomProgressBar @Inject constructor(@ActivityContext val context: Context) {

    private val progressBar: ProgressBar

    private val activity = (context as AppCompatActivity)

    init {
        val layout = activity.findViewById<View>(R.id.content).rootView as ViewGroup
        progressBar = ProgressBar(context, null, R.attr.progressBarStyle)
        progressBar.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        val rl = RelativeLayout(context)
        rl.gravity = Gravity.CENTER
        rl.addView(progressBar)
        layout.addView(rl, params)
        hide()
    }

    fun show() {
        if (activity.isFinishing.not()) {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun hide() {
        if (activity.isFinishing.not()) {
            progressBar.visibility = View.GONE
        }
    }
}