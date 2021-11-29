package com.khairo.youtubeandfacebookanimation.youtube

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.models.youtube.YoutubeModel

class MainViewModel : ViewModel() {

    private val _isVideoAppeared = ObservableField(false)
    val isVideoAppeared get() = _isVideoAppeared

    val videos = MutableLiveData<List<YoutubeModel>>().apply {
        val array = ArrayList<YoutubeModel>()

        array.add(YoutubeModel(R.drawable.cat_1, R.drawable.cat_11, "Title one", "Description one"))
        array.add(
            YoutubeModel(
                R.drawable.cat_2,
                R.drawable.cat_12,
                "Title two two two two two two two two two two two two two two",
                "Description two two two two two two two two two "
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_3,
                R.drawable.cat_13,
                "Title three",
                "Description three"
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_4,
                R.drawable.cat_14,
                "Title four",
                "Description four"
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_5,
                R.drawable.cat_15,
                "Title five five five five five five five five five five five five five five",
                "Description five five five five five five five five five five five five five"
            )
        )
        array.add(YoutubeModel(R.drawable.cat_6, R.drawable.cat_16, "Title six", "Description six"))
        array.add(
            YoutubeModel(
                R.drawable.cat_7,
                R.drawable.cat_17,
                "Title seven",
                "Description seven"
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_8,
                R.drawable.cat_18,
                "Title eight eight eight eight eight eight eight",
                "Description eight eight eight eight eight eight eight eight eight eight eight eight eight eight"
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_9,
                R.drawable.cat_19,
                "Title nine",
                "Description nine nine nine nine nine nine nine nine nine nine"
            )
        )
        array.add(
            YoutubeModel(
                R.drawable.cat_10,
                R.drawable.cat_10,
                "Title ten",
                "Description ten"
            )
        )

        value = array
    }

    fun changeAmount(newAmount: Boolean) {
        _isVideoAppeared.set(newAmount)
    }
}
