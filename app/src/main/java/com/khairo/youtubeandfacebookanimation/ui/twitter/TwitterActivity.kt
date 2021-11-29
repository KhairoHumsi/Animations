package com.khairo.youtubeandfacebookanimation.ui.twitter

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityTwitterBinding

class TwitterActivity : BaseActivity<ActivityTwitterBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_twitter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.motionLayout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)
    }
}