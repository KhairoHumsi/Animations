package com.khairo.youtubeandfacebookanimation.facebook

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityFacebookBinding
import com.khairo.youtubeandfacebookanimation.youtube.MainAdapter

class FacebookActivity : BaseActivity<ActivityFacebookBinding>() {

    private lateinit var viewModel: FacebookViewModel
    private lateinit var adapter: StoryAdapter

    override fun getLayoutId(): Int = R.layout.activity_facebook

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.myStoryIcon.clipToOutline = true
//        binding.myStoryAdd.clipToOutline = true

        binding.motionLayout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)
//        binding.myStory.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)

        viewModel = ViewModelProvider(this).get(FacebookViewModel::class.java)


        viewModel.stories.observe(this, {
            adapter = StoryAdapter(it)
            binding.activityFacebookRecycler.adapter = adapter

        })

//        binding.activityFacebookRecycler.setOnScrollChangeListener { _, _, _, _, _ ->
//            binding.myStoryIcon.clipToOutline = "${binding.motionLayout.transitionState["motion.progress"]}" == "1.0"
//        }
    }

}
