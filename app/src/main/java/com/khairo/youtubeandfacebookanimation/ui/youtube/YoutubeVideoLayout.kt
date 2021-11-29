package com.khairo.youtubeandfacebookanimation.ui.youtube

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.utils.MultiListenerMotionLayout
import com.khairo.youtubeandfacebookanimation.utils.bindView
import kotlinx.coroutines.launch

class YoutubeVideoLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    private val closeVideo: ImageView by bindView(R.id.close_video)
    val bottomNavigationView: BottomNavigationView by bindView(R.id.bottomNavigationView)

    init {
        inflate(context, R.layout.youtube_video_layout, this)
        enableClicks()
    }

    private fun enableClicks() = when (currentState) {
        R.id.base -> {}
        R.id.show -> {}
        R.id.midShow -> {
            closeVideo.setOnClickListener { closeVideo() }
        }
        else -> {
            Log.d(
                "ASDfgh",
                "sdsfd ${currentState}, ${R.id.base}, ${R.id.show}, ${R.id.midShow}"
            )
            throw IllegalStateException("Can be called only for the permitted 3 currentStates")
        }
    }

    fun openVideo(): Unit = performAnimation {

        setTransition(R.id.base, R.id.show)

        // 1) base -> show
        transitionToState(R.id.show)
        awaitTransitionComplete(R.id.show)

    }

    private fun closeVideo(): Unit = performAnimation {

        // 1) midShow -> end
        setTransition(R.id.midShow, R.id.base)
        progress = 1f
        transitionToStart()
        awaitTransitionComplete(R.id.base)

    }

    private fun disableClicks() {
        closeVideo.setOnClickListener(null)
    }

    private inline fun performAnimation(crossinline block: suspend () -> Unit) {
        (context as YoutubeActivity).lifecycleScope.launch {
            disableClicks()
            block()
            enableClicks()
        }
    }
}
