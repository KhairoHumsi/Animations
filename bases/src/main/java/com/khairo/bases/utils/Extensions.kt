package com.khairo.bases.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * these some extensions and functions for repeating using
 *
 */

fun DialogFragment.transparentBachGround() {
    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Int.setAnimation(
    viewToAnimate: View,
    lastPosition: Int,
    fromX: Float,
    toX: Float,
    fromY: Float,
    toY: Float,
    animation: Int
): Int =
    if (this > lastPosition) {
        ScaleAnimation(
            fromX, toX, fromY, toY,
            animation, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).run {
            duration = Random().nextInt(750).toLong()
            viewToAnimate.startAnimation(this)

        }
        this
    } else this
