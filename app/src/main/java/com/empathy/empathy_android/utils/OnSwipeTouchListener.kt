package com.empathy.empathy_android.utils

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.GestureDetector.SimpleOnGestureListener
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.view.View.OnTouchListener
import androidx.core.content.ContextCompat.startActivity


class OnSwipeTouchListener(ctx: Context, listener: OnViewTransitionListener?) : OnTouchListener {

    interface OnViewTransitionListener {
        fun viewTransitioned()
    }

    private var gestureDetector: GestureDetector
    private val context : Context = ctx

    private var listener: OnViewTransitionListener? = null

    val HORIZONTAL_MIN_DISTANCE = 40
    val VERTICAL_MIN_DISTANCE = 80

    enum class Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private val logTag = "SwipeDetector"
    private val MIN_DISTANCE = 100
    private var downX: Float = 0.toFloat()
    var downY: Float = 0.toFloat()
    var upX: Float = 0.toFloat()
    var upY: Float = 0.toFloat()
    private var mSwipeDetected = Action.None

    fun swipeDetected(): Boolean {
        return mSwipeDetected != Action.None
    }

    fun getAction(): Action {
        return mSwipeDetected
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())

        this.listener = listener
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                mSwipeDetected = Action.None
//                listener?.viewTransitioned()
                return false // allow other events like Click to be processed
            }
            MotionEvent.ACTION_MOVE -> {
                upX = event.x
                upY = event.y

                val deltaX = downX - upX
                val deltaY = downY - upY

                // horizontal swipe detection
                if(Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
                    // left or right
                    if(deltaX < 0) {
                        Log.i(logTag, "Swipe Left to Right")
                        mSwipeDetected = Action.LR
                        return true
                    }
                    if(deltaX > 0) {
                        Log.i(logTag, "Swipe Right to Left")
                        mSwipeDetected = Action.RL
                        return true
                    }
                } else

                // vertical swipe detection
                    if(Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
                        // top or down
                        if(deltaY < 0) {
                            Log.i(logTag, "Swipe Top to Bottom")
                            mSwipeDetected = Action.TB
                            return false
                        }
                        if(deltaY > 0) {
                            Log.i(logTag, "Swipe Bottom to Top")
                            mSwipeDetected = Action.BT
                            return false
                        }
                    }
                return true
            }
        }
        return false

//        when(event.action) {
//            MotionEvent.ACTION_DOWN -> {}
//        }
//
//        return gestureDetector.onTouchEvent(event)
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
        listener?.viewTransitioned()
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