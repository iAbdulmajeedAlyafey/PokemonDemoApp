package com.example.demoapp.ui.common.base.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel
import com.example.demoapp.ui.common.view.CustomProgressBar
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private val viewModel: BaseViewModel by viewModels()

    var binding: VB? = null
        private set

    @Inject
    protected lateinit var progressBar: CustomProgressBar

    protected abstract fun onBindView(): VB?

    protected open fun setupViews(): VB? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onBindView()?.also { setContentView(it.root) }
        setupViews()
        setOnClickListeners()
    }

    protected open fun setOnClickListeners() = binding

    open fun showLoading() = progressBar.show()

    open fun hideLoading() = progressBar.hide()

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}