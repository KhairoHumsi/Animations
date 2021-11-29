package com.khairo.youtubeandfacebookanimation.ui.marvel

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.GridLayoutManager
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityMarvelBinding
import com.khairo.youtubeandfacebookanimation.utils.ItemTouchInterceptor
import com.khairo.youtubeandfacebookanimation.utils.load
import com.khairo.youtubeandfacebookanimation.utils.setTransitionListener

class MarvelActivity : BaseActivity<ActivityMarvelBinding>(), CharacterAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.activity_marvel

    private val itemTouchInterceptor = ItemTouchInterceptor()

    companion object {
        const val TAG = "MotionRecyclerView"
        const val SPAN_COUNT = 3
        const val NO_CLIP = 0
        const val CLIP_TOP = 1
        const val CLIP_BOTTOM = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()
//        binding.motion.setOnApplyWindowInsetsListener { v, insets ->
//            binding.toolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
//            insets
//        }
        init()

    }

    private fun setStatusBarTransparent() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun init() {
        setupAdapter()
        binding.rvImage.addOnItemTouchListener(itemTouchInterceptor)
    }

    private fun setupAdapter() {
        binding.rvImage.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.rvImage.adapter = CharacterAdapter(DataSource().data, this)
    }

    override fun onItemClick(view: View, position: Int, data: CharacterAdapter.Data) {
        val viewGroup = view as? ViewGroup
        val img = viewGroup?.run { findViewById<AppCompatImageView>(R.id.img) } ?: return

        binding.imgHeader.load(data.headerRes)
        binding.textTitle.text = getString(data.title)
        binding.textDesc.text = getString(data.desc)

        val rect = Rect()
        img.getLocalVisibleRect(rect)
        val clipType = when {
            rect.height() == img.height -> NO_CLIP
            rect.top > 0 -> CLIP_TOP
            else -> CLIP_BOTTOM
        }

        Log.d(TAG, "clip type: $clipType")
        binding.motion.offsetDescendantRectToMyCoords(img, rect)
        Log.d(TAG, "localVisibleRect in parent coord system: $rect")

        val set = binding.motion.getConstraintSet(R.id.start)
        set.clear(R.id.img_motion)
        set.constrainWidth(R.id.img_motion, img.width)
        set.constrainHeight(R.id.img_motion, img.height)
        set.connect(
            R.id.img_motion,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            rect.left
        )
        when (clipType) {
            CLIP_TOP -> set.connect(
                R.id.img_motion,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                binding.motion.bottom - rect.bottom
            )
            else -> set.connect(
                R.id.img_motion,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                rect.top
            )
        }

        img.alpha = 0.0f
        binding.imgMotion.visibility = View.VISIBLE
        binding.imgMotion.setImageDrawable(img.drawable)
        binding.motion.apply {
            updateState(R.id.start, set)
            setTransition(R.id.start, R.id.end)
            setTransitionListener({ start, end ->
                itemTouchInterceptor.enable()
                if (start == startState) {
                    img.alpha = 0.0f
                    binding.imgMotion.alpha = 1.0f
                }
            },
                { state ->
                    if (state == startState) {
                        itemTouchInterceptor.disable()
                        img.alpha = 1.0f
                        binding.imgMotion.alpha = 0.0f
                    }
                })
            transitionToEnd()
        }
    }

    override fun onBackPressed() {
        if (binding.motion.currentState != binding.motion.startState) {
            binding.motion.transitionToStart()
        } else super.onBackPressed()
    }
}
