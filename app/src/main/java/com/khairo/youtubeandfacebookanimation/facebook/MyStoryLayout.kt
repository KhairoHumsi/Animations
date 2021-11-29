package com.khairo.youtubeandfacebookanimation.facebook

import android.content.Context
import android.util.AttributeSet
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.utils.MultiListenerMotionLayout

class MyStoryLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.my_story_layout, this)
    }
}