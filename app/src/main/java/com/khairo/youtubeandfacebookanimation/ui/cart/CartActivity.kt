package com.khairo.youtubeandfacebookanimation.ui.cart

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityCartBinding
import com.khairo.youtubeandfacebookanimation.utils.ItemTouchInterceptor
import com.khairo.youtubeandfacebookanimation.utils.transitionListener
import kotlinx.coroutines.launch

class CartActivity : BaseActivity<ActivityCartBinding>(), ItemsAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.activity_cart

    private val itemTouchInterceptor = ItemTouchInterceptor()

    companion object {
        const val TAG = "CartActivity"
        const val SPAN_COUNT = 2
        const val NO_CLIP = 0
        const val CLIP_TOP = 1
        const val CLIP_BOTTOM = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding.cartMotionLayout.transitionToStart()
        setupAdapter()
        binding.itemsRecycler.addOnItemTouchListener(itemTouchInterceptor)
    }

    private fun setupAdapter() {
        binding.itemsRecycler.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.itemsRecycler.adapter = ItemsAdapter(this, DataSource().data, this)
    }

    override fun onItemClick(view: View, position: Int, data: ItemsAdapter.Data) {
        val viewGroup = view as? ViewGroup
        val cartCellImage = viewGroup?.run { findViewById<AppCompatImageView>(R.id.cart_cell_image) } ?: return

        val rect = Rect()
        cartCellImage.getLocalVisibleRect(rect)
        val clipType = when {
            rect.height() == cartCellImage.height -> NO_CLIP
            rect.top > 0 -> CLIP_TOP
            else -> CLIP_BOTTOM
        }

        Log.d(TAG, "clip type: $clipType")
        binding.cartMotionLayout.offsetDescendantRectToMyCoords(cartCellImage, rect)
        Log.d(TAG, "localVisibleRect in parent coord system: $rect")

        val set = binding.cartMotionLayout.getConstraintSet(R.id.cart_base)
        set.clear(R.id.cart_image_container)
        set.constrainWidth(R.id.cart_image_container, cartCellImage.width)
        set.constrainHeight(R.id.cart_image_container, cartCellImage.height)
        set.connect(
            R.id.cart_image_container,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            rect.left
        )
        when (clipType) {
            CLIP_TOP -> set.connect(
                R.id.cart_image_container,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                binding.cartMotionLayout.bottom - rect.bottom
            )
            else -> set.connect(
                R.id.cart_image_container,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                rect.top
            )
        }

//        cartCellImage.alpha = 0.0f
//        binding.cartImageContainer.visibility = View.VISIBLE
        binding.cartImage.setImageDrawable(cartCellImage.drawable)
        binding.cartMotionLayout.apply {
            updateState(R.id.cart_base, set)
            setTransition(R.id.cart_base, R.id.cart_add)
            addTransitionListeners(transitionListener({ start, end ->
//                itemTouchInterceptor.enable()
                if (start == startState) {
//                    cartCellImage.alpha = 0.0f
                    binding.cartImageContainer.alpha = 1.0f
                }
            },
                { state ->
                    if (state == startState) {
//                        itemTouchInterceptor.disable()
//                        cartCellImage.alpha = 1.0f
                        binding.cartImageContainer.alpha = 0.0f
                    }
                }))

            lifecycleScope.launch {
                // 1) cart_base -> cart_add
                transitionToState(R.id.cart_add)
                awaitTransitionComplete(R.id.cart_add)

                // 1) cart_add -> cart_add_two
                transitionToState(R.id.cart_add_two)
                awaitTransitionComplete(R.id.cart_add_two)

                // 2) cart_add_two -> cart_add_three
                transitionToState(R.id.cart_add_three)
                awaitTransitionComplete(R.id.cart_add_three)

                // 2) cart_add_three -> cart_base_back
                transitionToState(R.id.cart_base_back)
                awaitTransitionComplete(R.id.cart_base_back)

            }
        }
    }

//    override fun onBackPressed() {
//        if (binding.cartMotionLayout.currentState != binding.cartMotionLayout.startState) {
//            binding.cartMotionLayout.transitionToStart()
//        } else super.onBackPressed()
//    }
}
