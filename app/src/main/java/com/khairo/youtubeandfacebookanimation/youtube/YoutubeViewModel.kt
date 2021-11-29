package com.khairo.youtubeandfacebookanimation.youtube

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.khairo.youtubeandfacebookanimation.models.youtube.YoutubeModel

class YoutubeViewModel : ViewModel() {

    private val _isVideoAppeared = ObservableField(false)
    val isVideoAppeared get() = _isVideoAppeared

    private var _video: YoutubeModel? = YoutubeModel()
    val video get() = _video

    fun changeVideo(video: YoutubeModel?) {
        if (video == null) _isVideoAppeared.set(false)
        else _isVideoAppeared.set(true)

        _video = video

    }
}
