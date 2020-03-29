package com.vandenbreemen.kevindesignsystem.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 *
 * @author kevin
 */
class KDSSpinnerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var startAngle = -180f
    var sweepAngle = 180f

    var currentStart = startAngle

    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10.0f
    }

    fun setAngleOffset(factor: Float) {
        currentStart = (startAngle + factor)

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(0.0f, 0.0f, 200.0f, 200.0f, currentStart, sweepAngle, false, arcPaint)

    }

}