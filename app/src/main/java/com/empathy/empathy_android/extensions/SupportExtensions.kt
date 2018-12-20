package com.empathy.empathy_android.extensions

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


val Int.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density).toFloat()

val Int.toDp: Float
    get() = (this / Resources.getSystem().displayMetrics.density).toFloat()

fun ImageView.loadImage(imageUrl: String)
        = Glide.with(context)
        .load(imageUrl)
        .apply(RequestOptions()
                .override(720, 540))
        .into(this)