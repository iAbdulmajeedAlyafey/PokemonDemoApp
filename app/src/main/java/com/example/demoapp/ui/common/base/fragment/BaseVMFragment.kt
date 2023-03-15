package com.example.demoapp.ui.common.base.fragment

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.demoapp.ui.common.base.viewmodel.BaseViewModel

abstract class BaseVMFragment<VB : ViewBinding> : BaseFragment<VB>() {

    protected abstract val viewModel: BaseViewModel

    protected abstract fun observeUi()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUi()
    }
}
