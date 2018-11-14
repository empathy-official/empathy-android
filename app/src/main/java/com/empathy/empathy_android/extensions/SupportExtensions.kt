package com.empathy.empathy_android.extensions

import android.content.res.Resources


val Int.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density).toFloat()

val Int.toDp: Float
    get() = (this / Resources.getSystem().displayMetrics.density).toFloat()