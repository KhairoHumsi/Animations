package com.khairo.youtubeandfacebookanimation

import android.os.Bundle
import com.khairo.bases.data.BaseActivity
import com.khairo.youtubeandfacebookanimation.cart.CartActivity
import com.khairo.youtubeandfacebookanimation.custom.CustomActivity
import com.khairo.youtubeandfacebookanimation.databinding.ActivityMainBinding
import com.khairo.youtubeandfacebookanimation.facebook.FacebookActivity
import com.khairo.youtubeandfacebookanimation.marvel.MarvelActivity
import com.khairo.youtubeandfacebookanimation.music.MusicActivity
import com.khairo.youtubeandfacebookanimation.twitter.TwitterActivity
import com.khairo.youtubeandfacebookanimation.utils.start
import com.khairo.youtubeandfacebookanimation.youtube.YoutubeActivity

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
