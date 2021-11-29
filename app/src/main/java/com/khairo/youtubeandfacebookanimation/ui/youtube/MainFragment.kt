package com.khairo.youtubeandfacebookanimation.ui.youtube

import androidx.lifecycle.ViewModelProvider
import com.khairo.bases.data.BaseFragment
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onStart() {
        super.onStart()

        viewModel.videos.observe(requireActivity(), {
            mainAdapter = MainAdapter(it, viewModel)
//            binding.mainFragmentRecycler.adapter = mainAdapter

        })

        binding.test.setOnClickListener {
            YoutubeActivity.youtubeActivity.changeState()
        }
    }

    override fun setBindingVariables() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun setUpViewAndActions() { }
}