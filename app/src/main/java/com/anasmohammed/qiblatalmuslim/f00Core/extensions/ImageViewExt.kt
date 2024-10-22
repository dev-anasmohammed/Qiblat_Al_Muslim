package com.anasmohammed.qiblatalmuslim.f00Core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun <T> ImageView.loadImage(imagePath: T ) {
    Glide.with(this).load(imagePath).dontAnimate().into(this)
}