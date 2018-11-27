package com.empathy.empathy_android.utils

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.GestureDetector.SimpleOnGestureListener
import android.text.method.Touch.onTouchEvent
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.view.View.OnTouchListener
import androidx.core.content.ContextCompat.startActivity
import com.empathy.empathy_android.ui.camera.CameraActivity


class OnSwipeTouchListener(ctx: Context) : OnTouchListener {

    private var gestureDetector: GestureDetector
    private val context : Context = ctx

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    }
                    result = true
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                }
                result = true

            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

    fun onSwipeRight() {
        Log.d(TAG, "onSwipeRight")
        startActivity(context, Intent(context, CameraActivity::class.java),null)
    }

    fun onSwipeLeft() {
        Log.d(TAG, "onSwipeLeft")
    }

    fun onSwipeTop() {
        Log.d(TAG, "onSwipeTop")
    }

    fun onSwipeBottom() {
        Log.d(TAG, "onSwipeBottom")
    }

    companion object {
        private val TAG = "OnSwipeTouchListener"
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }
}