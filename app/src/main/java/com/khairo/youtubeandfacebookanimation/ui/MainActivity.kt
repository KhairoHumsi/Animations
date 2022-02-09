package com.khairo.youtubeandfacebookanimation.ui

import android.os.Bundle
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.R
import com.khairo.youtubeandfacebookanimation.databinding.ActivityMainBinding
import com.khairo.youtubeandfacebookanimation.ui.cart.CartActivity
import com.khairo.youtubeandfacebookanimation.ui.custom.CustomActivity
import com.khairo.youtubeandfacebookanimation.ui.facebook.FacebookActivity
import com.khairo.youtubeandfacebookanimation.ui.marvel.MarvelActivity
import com.khairo.youtubeandfacebookanimation.ui.music.MusicActivity
import com.khairo.youtubeandfacebookanimation.ui.twitter.TwitterActivity
import com.khairo.youtubeandfacebookanimation.ui.youtube.YoutubeActivity
import com.khairo.youtubeandfacebookanimation.utils.start

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            youtube.setOnClickListener {
                start(YoutubeActivity())
            }

            cart.setOnClickListener {
                start(CartActivity())
            }

            marvel.setOnClickListener {
                start(MarvelActivity())
            }

            custom.setOnClickListener {
                start(CustomActivity())
            }

            music.setOnClickListener {
                start(MusicActivity())
            }

            twitter.setOnClickListener {
                start(TwitterActivity())
            }

            facebook.setOnClickListener {
                start(FacebookActivity())
            }
        }
    }

}
