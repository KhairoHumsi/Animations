package com.khairo.youtubeandfacebookanimation.ui.facebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.models.facebook.FacebookModel

class FacebookViewModel : ViewModel() {

    val stories = MutableLiveData<List<FacebookModel>>().apply {
        val array = ArrayList<FacebookModel>()

        array.add(FacebookModel(R.drawable.cat_1, R.drawable.cat_11, "Title one"))
        array.add(
            FacebookModel(
                R.drawable.cat_2,
                R.drawable.cat_12,
                "Title two two two two two two two two two two two two two two"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_3,
                R.drawable.cat_13,
                "Title three"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_4,
                R.drawable.cat_14,
                "Title four"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_5,
                R.drawable.cat_15,
                "Title five five five five five five five five five five five five five five"
            )
        )
        array.add(FacebookModel(R.drawable.cat_6, R.drawable.cat_16, "Title six"))
        array.add(
            FacebookModel(
                R.drawable.cat_7,
                R.drawable.cat_17,
                "Title seven"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_8,
                R.drawable.cat_18,
                "Title eight eight eight eight eight eight eight"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_9,
                R.drawable.cat_19,
                "Title nine"
            )
        )
        array.add(
            FacebookModel(
                R.drawable.cat_10,
                R.drawable.cat_10,
                "Title ten"
            )
        )

        value = array
    }
}
