package com.example.demoapp.ui.common.delegates.progressbar

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.demoapp.ui.common.view.CustomProgressBar
import java.lang.ref.WeakReference

interface ProgressBar {

    fun setUpProgressBar(fragment: Fragment)

    fun setLoading(isLoading: Boolean)

    fun showLoading()

    fun hideLoading()
}

class ProgressBarImpl : ProgressBar, DefaultLifecycleObserver {

    private lateinit var progressBar: CustomProgressBar

    private lateinit var fragmentRef: WeakReference<Fragment>

    override fun setUpProgressBar(fragment: Fragment) {
        fragmentRef = WeakReference(fragment)
        fragmentRef.get()?.apply {
            progressBar = CustomProgressBar(requireActivity())
            lifecycle.addObserver(this@ProgressBarImpl)
        }
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