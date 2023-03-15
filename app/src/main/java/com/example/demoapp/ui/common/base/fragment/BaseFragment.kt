package com.example.demoapp.ui.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.demoapp.util.CustomProgressBar
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    var binding: VB? = null
        private set

    @Inject
    lateinit var progressBar: CustomProgressBar

    protected lateinit var navController: NavController

    protected abstract fun onBind(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun setupViews(): VB? = null

    protected open fun setOnClickListeners() = binding

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = onBind(inflater, container)
        return binding?.root ?: return null
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavController()
        setupViews()
        setOnClickListeners()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        setOnTextChangeListeners()
    }

    @CallSuper
    override fun onStop() {
        hideLoading()
        super.onStop()
    }

    private fun setupNavController() = runCatching { navController = findNavController() }

    open fun setOnTextChangeListeners(): VB? = null

    open fun setLoading(isLoading: Boolean) = if (isLoading) showLoading() else hideLoading()

    open fun showLoading() = progressBar.show()

    open fun hideLoading() = progressBar.hide()

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
