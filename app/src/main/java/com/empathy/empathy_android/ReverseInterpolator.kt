package com.empathy.empathy_android

import android.view.animation.Interpolator


internal class ReverseInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return Math.abs(input - 1.0f)
    }
}