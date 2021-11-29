package com.khairo.youtubeandfacebookanimation.ui.youtube

import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityYoutubeBinding

class YoutubeActivity : BaseActivity<ActivityYoutubeBinding>() {

    private lateinit var viewModel: YoutubeViewModel

    override fun getLayoutId(): Int = R.layout.activity_youtube

    override fun onCreate(savedInstanceState: Bundle?) {
        youtubeActivity = this
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

//            MotionLayout.DEBUG_SHOW_PATH
//            MotionLayout.DEBUG_SHOW_NONE
        binding.motionLayout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)


    }

    override fun setUpViewAndActions() {
        viewModel = ViewModelProvider(this).get(YoutubeViewModel::class.java)
    }

    fun changeState() {
        binding.motionLayout.transitionToState(R.id.show)
        Log.d("sadfgnhfd", "sadfghf: ${binding.motionLayout.transitionState["motion.EndState"]}, ${R.id.show}")

//
//        if (binding.motionLayout.progress > 0.5f) {
//            binding.motionLayout.transitionToState(R.id.show)
//            binding.motionLayout.transitionToStart()
//        } else {
//            binding.motionLayout.transitionToEnd()
//        }
    }


    companion object {
        lateinit var youtubeActivity: YoutubeActivity
    }
}
