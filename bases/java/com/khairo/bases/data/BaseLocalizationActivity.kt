package com.khairo.bases.data

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity

abstract class BaseLocalizationActivity<VB : ViewDataBinding> : LocalizationActivity() {

    private lateinit  var _binding: VB
    open val binding get() = _binding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayoutId())
    }
}
