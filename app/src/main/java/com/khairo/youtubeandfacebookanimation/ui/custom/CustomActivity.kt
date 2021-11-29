package com.khairo.youtubeandfacebookanimation.ui.custom

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityCustomBinding

var animationPlaybackSpeed: Double = 0.8

class CustomActivity : BaseActivity<ActivityCustomBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_custom

    private lateinit var mainListAdapter: MainListAdapter

    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / animationPlaybackSpeed).toLong()

    var isAdapterFiltered: Boolean
        get() = mainListAdapter.isFiltered
        set(value) {
            mainListAdapter.isFiltered = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RecyclerView Init
        mainListAdapter = MainListAdapter(this)
        binding.apply {
            recyclerView.adapter = mainListAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@CustomActivity)
            recyclerView.setHasFixedSize(true)
            recyclerView.updateRecyclerViewAnimDuration()
        }
    }

    private fun RecyclerView.updateRecyclerViewAnimDuration() = itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)
}