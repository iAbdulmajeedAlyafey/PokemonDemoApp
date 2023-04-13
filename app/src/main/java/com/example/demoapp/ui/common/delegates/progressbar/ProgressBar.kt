package com.example.demoapp.ui.common.delegates.progressbar

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.demoapp.ui.common.view.CustomProgressBar

interface ProgressBar {

    fun setUpProgressBar(activity: FragmentActivity)

    fun setLoading(isLoading: Boolean)

    fun showLoading()

    fun hideLoading()
}

class ProgressBarImpl : ProgressBar, DefaultLifecycleObserver {

    private lateinit var progressBar: CustomProgressBar

    override fun setUpProgressBar(activity: FragmentActivity) {
        progressBar = CustomProgressBar(activity)
        activity.lifecycle.addObserver(this)
    }

    override fun setLoading(isLoading: Boolean) = if (isLoading) showLoading() else hideLoading()

    override fun showLoading() {
        if (::progressBar.isInitialized) progressBar.show()
    }

    override fun hideLoading() {
        if (::progressBar.isInitialized) progressBar.hide()
    }

    override fun onStop(owner: LifecycleOwner) = hideLoading()
}