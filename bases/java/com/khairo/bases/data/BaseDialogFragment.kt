package com.khairo.bases.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.khairo.bases.utils.transparentBachGround

abstract class BaseDialogFragment<VB : ViewDataBinding> : DialogFragment() {

    private lateinit  var _binding: VB
    open val binding get() = _binding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        transparentBachGround()
        return _binding.root
    }

    abstract fun setUpViewAndActions()

}
