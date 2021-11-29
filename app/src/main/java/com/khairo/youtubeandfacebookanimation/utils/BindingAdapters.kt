package com.khairo.youtubeandfacebookanimation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khairo.youtubeandfacebookanimation.R

@BindingAdapter("imagePath")
fun bindImageFromPath(imageView: ImageView, imagePath: Int) {
    Glide.with(imageView.context)
        .load(imagePath)
        .placeholder(R.drawable.ic_placeholder)
        .into(imageView)

}

//@BindingAdapter("normalImage")
//fun loadNormalImage(imageView: ImageView, image: String) {
//    Glide.with(imageView.context)
//        .load("http://globalhealth-news.com/img/$image".replace(" ", "%20"))
//        .fitCenter()
//        .into(imageView)
//
//}
