package com.khairo.youtubeandfacebookanimation.twitter

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.utils.MultiListenerMotionLayout
import com.khairo.youtubeandfacebookanimation.utils.bindView
import kotlinx.coroutines.launch

class TwitterFabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    private val twitterFabIcon: ImageView by bindView(R.id.twitter_fab_icon)
    private val twitterFabIcon2: ImageView by bindView(R.id.twitter_fab_icon2)

    init {
        inflate(context, R.layout.layout_twitter_fab, this)
        enableClicks()
    }

    private fun enableClicks() = when (currentState) {
        R.id.twitter_start -> twitterFabIcon.setOnClickListener { openSheet() }

        R.id.twitter_end -> twitterFabIcon2.setOnClickListener { closeSheet() }


        else -> {
            Log.d(
                "ASDfgh",
                "sdsfd ${currentState}, ${R.id.twitter_start}, ${R.id.twitter_end}"
            )
            throw IllegalStateException("Can be called only for the permitted 3 currentStates")
        }
    }

    private fun openSheet(): Unit = performAnimation {

        setTransition(R.id.twitter_start, R.id.twitter_end)

        // 1) start -> end
        transitionToState(R.id.twitter_end)
        awaitTransitionComplete(R.id.twitter_end)


    }

    private fun closeSheet(): Unit = performAnimation {

        // 1) set4_settle -> set3_reveal
        setTransition(R.id.twitter_start, R.id.twitter_end)
        progress = 1f
        transitionToStart()
        awaitTransitionComplete(R.id.twitter_start)

    }
//
//    private fun onFilterApplied(): Unit = performAnimation {
//
////        if (!tabsHandler.hasActiveFilters) return@performAnimation
//
//        // 1) set4_settle -> set5_filterCollapse
//        transitionToState(R.id.set5_filterCollapse)
//        awaitTransitionComplete(R.id.set5_filterCollapse)
//
//        // 2) set5_filterCollapse -> set6_filterLoading
//        (context as CustomActivity).isAdapterFiltered = true
//        transitionToState(R.id.set6_filterLoading)
//        awaitTransitionComplete(R.id.set6_filterLoading)
//
//        // 3) set6_filterLoading -> set7_filterBase
//        // (Start scale 'up' animation simultaneously)
//        transitionToState(R.id.set7_filterBase)
//        startScaleDownAnimator(false)
//        awaitTransitionComplete(R.id.set7_filterBase)
////
////        // Remove adapters
////        tabsHandler.setAdapters(false)
//    }
//
//    private fun unFilterAdapterItems(): Unit = performAnimation {
//        // 1) set7_filterBase -> set8_unfilterInset
//        // (Start scale down animation simultaneously)
//        transitionToState(R.id.set8_unfilterInset)
//        startScaleDownAnimator(true)
//        awaitTransitionComplete(R.id.set8_unfilterInset)
//
//        // 2) set8_unfilterInset -> set9_unfilterLoading
//        // (Un-filter adapter items simultaneously)
//        (context as CustomActivity).isAdapterFiltered = false
//        awaitTransitionComplete(R.id.set9_unfilterLoading)
////
//        // 3) set9_unfilterLoading -> set10_unfilterOutset
//        // (Start scale 'up' animation simultaneously)
//        startScaleDownAnimator(false)
//        awaitTransitionComplete(R.id.set10_unfilterOutset)
//    }

    private fun disableClicks() {
        twitterFabIcon.setOnClickListener(null)
    }

    private inline fun performAnimation(crossinline block: suspend () -> Unit) {
        (context as TwitterActivity).lifecycleScope.launch {
            disableClicks()
            block()
            enableClicks()
        }
    }
}
