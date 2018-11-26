package com.empathy.empathy_android.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


internal class RoundImageView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null

): AppCompatImageView(context, attributeSet) {

    private val rectF    = RectF()
    private val clipPath = Path()
    private val paint    = Paint()

    override fun onDraw(canvas: Canvas?) {
        clipPath.addCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2), Path.Direction.CW)

        canvas?.drawCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2) - 1f, paint)
        canvas?.clipPath(clipPath)

        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        val screenHeight = MeasureSpec.getSize(heightMeasureSpec)

        rectF.set(0F, 0F, screenWidth.toFloat(), screenHeight.toFloat())
    }

}