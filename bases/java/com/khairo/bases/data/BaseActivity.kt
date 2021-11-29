package com.khairo.bases.data

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    private lateinit  var _binding: VB
    open val binding get() = _binding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingVariables()
        _binding = DataBindingUtil.setContentView(this, getLayoutId())
        setUpViewAndActions()
    }

    abstract fun setBindingVariables()

    abstract fun setUpViewAndActions()
}
