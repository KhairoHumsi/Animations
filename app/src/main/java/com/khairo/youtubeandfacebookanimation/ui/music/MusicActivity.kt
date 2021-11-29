package com.khairo.youtubeandfacebookanimation.ui.music

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityMusicBinding
import com.khairo.youtubeandfacebookanimation.utils.ItemTouchInterceptor
import com.khairo.youtubeandfacebookanimation.utils.SpaceItemDecoration
import com.khairo.youtubeandfacebookanimation.utils.transitionListener
import kotlinx.coroutines.launch

class MusicActivity : BaseActivity<ActivityMusicBinding>(), MusicAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.activity_music

    private val itemTouchInterceptor = ItemTouchInterceptor()

    companion object {
        const val TAG = "MusicActivity"
        const val SPAN_COUNT = 2
        const val NO_CLIP = 0
        const val CLIP_TOP = 1
        const val CLIP_BOTTOM = 2
    }

    private var musicCell: ConstraintLayout? = null
    var set: ConstraintSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        binding.musicPlaySecond.setOnClickListener {
            lifecycleScope.launch {
                // 2) music_first -> music_second
                binding.musicMotionLayout.transitionToState(R.id.music_second)
                binding.musicMotionLayout.awaitTransitionComplete(R.id.music_second)

            }
        }
    }

    private fun init() {
        binding.musicMotionLayout.transitionToStart()
        setupAdapter()
        binding.musicRecyclerview.addOnItemTouchListener(itemTouchInterceptor)
    }

    private fun setupAdapter() {
        binding.musicRecyclerview.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.musicRecyclerview.adapter = MusicAdapter(this, DataSource().data, this)

        binding.musicRecyclerview.addItemDecoration(SpaceItemDecoration(8))

    }

    override fun onItemClick(view: View, position: Int, data: MusicAdapter.Data) {
        val viewGroup = view as? ViewGroup
        musicCell = viewGroup?.run { findViewById(R.id.music_cell) } ?: return

        val musicCellImage =
            viewGroup.run { findViewById<AppCompatImageView>(R.id.music_cell_image) } ?: return

        setUp()

        binding.musicImageFirst.setImageDrawable(musicCellImage.drawable)
        binding.musicImageSecond.setImageDrawable(musicCellImage.drawable)
        binding.musicTitleFirst.text = getString(data.title)
        binding.musicTitleSecond.text = getString(data.title)
        binding.musicDescFirst.text = getString(data.desc)
        binding.musicDescSecond.text = getString(data.desc)
        binding.musicMotionLayout.apply {
            updateState(R.id.music_base, set)
            setTransition(R.id.music_base, R.id.music_first)
            addTransitionListeners(
                transitionListener({ start, end ->
                itemTouchInterceptor.enable()
                    if (start == startState) {
//                    cartCellImage.alpha = 0.0f
                        binding.musicContainer.alpha = 1.0f
                    }
                },
                    { state ->
                        if (state == startState) {
                        itemTouchInterceptor.disable()
//                        cartCellImage.alpha = 1.0f
                            set?.constrainWidth(R.id.music_container, 0)
                            set?.constrainHeight(R.id.music_container, 0)
                            binding.musicContainer.alpha = 0.0f
                        }
                    })
            )

            lifecycleScope.launch {
                // 1) music_base -> music_first
                transitionToState(R.id.music_first)
                awaitTransitionComplete(R.id.music_first)

            }
        }
    }

    private fun setUp() {
        val rect = Rect()
        musicCell?.getLocalVisibleRect(rect)
        val clipType = when {
            rect.height() == musicCell?.height -> NO_CLIP
            rect.top > 0 -> CLIP_TOP
            else -> CLIP_BOTTOM
        }

        Log.d(TAG, "clip type: $clipType")
        binding.musicMotionLayout.offsetDescendantRectToMyCoords(musicCell, rect)
        Log.d(TAG, "localVisibleRect in parent coord system: $rect")

        set = binding.musicMotionLayout.getConstraintSet(R.id.music_base)
        if (set == null) return

        set?.clear(R.id.music_container)
        set?.constrainWidth(R.id.music_container, musicCell?.width!!)
        set?.constrainHeight(R.id.music_container, musicCell?.height!!)
        set?.connect(
            R.id.music_container,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            rect.left
        )

        when (clipType) {
            CLIP_TOP -> set?.connect(
                R.id.music_container,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                binding.musicMotionLayout.bottom - rect.bottom
            )
            else -> set?.connect(
                R.id.music_container,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                rect.top
            )
        }
    }

    override fun onBackPressed() {
        when (binding.musicMotionLayout.currentState) {
            R.id.music_first -> {
                lifecycleScope.launch {
                    // 2) music_first -> music_base
                    setUp()
                    binding.musicMotionLayout.setTransition(R.id.music_base, R.id.music_first)
                    binding.musicMotionLayout.progress = 1f
                    binding.musicMotionLayout.transitionToStart()
                    binding.musicMotionLayout.awaitTransitionComplete(R.id.music_base)
                }
            }
            R.id.music_second -> {
                lifecycleScope.launch {
                    // 2) music_second -> music_first
                    binding.musicMotionLayout.setTransition(R.id.music_first, R.id.music_second)
                    binding.musicMotionLayout.progress = 1f
                    binding.musicMotionLayout.transitionToStart()
                    binding.musicMotionLayout.awaitTransitionComplete(R.id.music_second)
                }
            }
            else -> super.onBackPressed()
        }
    }
}
