package com.example.demoapp.ui.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    var binding: VB? = null
        private set

    protected lateinit var navController: NavController

    protected abstract fun onBind(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun setupViews(): VB? = null

    protected open fun setOnClickListeners(): VB? = binding

    protected open fun checkFragmentResultListener() = Unit

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        binding = onBind(inflater, container)
        return binding?.root ?: return null
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runCatching { navController = findNavController() }
        setupViews()
        setOnClickListeners()
        checkFragmentResultListener()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        setOnTextChangeListeners()
    }

    open fun setOnTextChangeListeners(): VB? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onUpButtonPressed()
        return true
    }

    open fun onUpButtonPressed() = onBackButtonPressed()

    open fun onBackButtonPressed() {
        navController.navigateUp()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}