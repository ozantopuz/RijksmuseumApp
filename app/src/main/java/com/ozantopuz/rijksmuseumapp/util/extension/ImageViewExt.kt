package com.ozantopuz.rijksmuseumapp.util.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozantopuz.rijksmuseumapp.R

fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(1280, 512).fitCenter())
            .into(this)
    } ?: kotlin.run {
        setImageResource(R.drawable.ic_baseline_image_24)
    }
}