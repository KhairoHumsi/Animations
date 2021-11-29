package com.khairo.youtubeandfacebookanimation.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.utils.MultiListenerMotionLayout
import com.khairo.youtubeandfacebookanimation.utils.bindView
import kotlinx.coroutines.launch

class FiltersMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    private val filterIcon: ImageView by bindView(R.id.filter_icon)
    private val closeIcon: ImageView by bindView(R.id.close_icon)

    init {
        inflate(context, R.layout.layout_filter_motion, this)
        enableClicks()
    }

    private fun enableClicks() = when (currentState) {
        R.id.set1_base -> {
            filterIcon.setOnClickListener { openSheet() }
        }
        R.id.set10_unfilterOutset -> {
            filterIcon.setOnClickListener { openSheet() }

        }
        R.id.set4_settle -> {
            filterIcon.setOnClickListener { onFilterApplied() }
            closeIcon.setOnClickListener { closeSheet() }
        }
        R.id.set7_filterBase -> {
            closeIcon.setOnClickListener {
                unFilterAdapterItems()
            }
            filterIcon.setOnClickListener(null)
        }

        else -> {
            Log.d(
                "ASDfgh",
                "sdsfd ${currentState}, ${R.id.set1_base}, ${R.id.set2_path}, ${R.id.set3_reveal}, ${R.id.set4_settle}, ${R.id.set5_filterCollapse}, ${R.id.set6_filterLoading}, ${R.id.set7_filterBase}, ${R.id.set8_unfilterInset}"
            )
            throw IllegalStateException("Can be called only for the permitted 3 currentStates")
        }
    }

    private fun openSheet(): Unit = performAnimation {

//        // Set adapters before opening filter sheet
//        tabsHandler.setAdapters(true)

        setTransition(R.id.set1_base, R.id.set2_path)

        // 1) set1_base -> set2_path
        transitionToState(R.id.set2_path)
        startScaleDownAnimator(true)
        awaitTransitionComplete(R.id.set2_path)

        // 2) set2_path -> set3_reveal
        transitionToState(R.id.set3_reveal)
        awaitTransitionComplete(R.id.set3_reveal)

        // 3) set3_reveal -> set4_settle
        transitionToState(R.id.set4_settle)
        awaitTransitionComplete(R.id.set4_settle)

    }

    private fun closeSheet(): Unit = performAnimation {

        // 1) set4_settle -> set3_reveal
        setTransition(R.id.set3_reveal, R.id.set4_settle)
        progress = 1f
        transitionToStart()
        awaitTransitionComplete(R.id.set3_reveal)

        // 2) set3_reveal -> set2_path
        setTransition(R.id.set2_path, R.id.set3_reveal)
        progress = 1f
        transitionToStart()
        awaitTransitionComplete(R.id.set2_path)

        startScaleDownAnimator(false)
        // 3) set2_path -> set1_base
        setTransition(R.id.set1_base, R.id.set2_path)
        progress = 1f
        transitionToStart()
        awaitTransitionComplete(R.id.set1_base)

//        // Remove adapters after closing filter sheet
//        tabsHandler.setAdapters(false)
    }

    private fun onFilterApplied(): Unit = performAnimation {

//        if (!tabsHandler.hasActiveFilters) return@performAnimation

        // 1) set4_settle -> set5_filterCollapse
        transitionToState(R.id.set5_filterCollapse)
        awaitTransitionComplete(R.id.set5_filterCollapse)

        // 2) set5_filterCollapse -> set6_filterLoading
        (context as CustomActivity).isAdapterFiltered = true
        transitionToState(R.id.set6_filterLoading)
        awaitTransitionComplete(R.id.set6_filterLoading)

        // 3) set6_filterLoading -> set7_filterBase
        // (Start scale 'up' animation simultaneously)
        transitionToState(R.id.set7_filterBase)
        startScaleDownAnimator(false)
        awaitTransitionComplete(R.id.set7_filterBase)
//
//        // Remove adapters
//        tabsHandler.setAdapters(false)
    }

    private fun unFilterAdapterItems(): Unit = performAnimation {
        // 1) set7_filterBase -> set8_unfilterInset
        // (Start scale down animation simultaneously)
        transitionToState(R.id.set8_unfilterInset)
        startScaleDownAnimator(true)
        awaitTransitionComplete(R.id.set8_unfilterInset)

        // 2) set8_unfilterInset -> set9_unfilterLoading
        // (Un-filter adapter items simultaneously)
        (context as CustomActivity).isAdapterFiltered = false
        awaitTransitionComplete(R.id.set9_unfilterLoading)
//
        // 3) set9_unfilterLoading -> set10_unfilterOutset
        // (Start scale 'up' animation simultaneously)
        startScaleDownAnimator(false)
        awaitTransitionComplete(R.id.set10_unfilterOutset)
    }

    private fun disableClicks() {
        filterIcon.setOnClickListener(null)
        closeIcon.setOnClickListener(null)
    }

    private inline fun performAnimation(crossinline block: suspend () -> Unit) {
        (context as CustomActivity).lifecycleScope.launch {
            disableClicks()
            block()
            enableClicks()
        }
    }

    private fun startScaleDownAnimator(isScaledDown: Boolean): Unit =
        (context as CustomActivity)
            .getAdapterScaleDownAnimator(isScaledDown)
            .apply { duration = transitionTimeMs }
            .start()
}
