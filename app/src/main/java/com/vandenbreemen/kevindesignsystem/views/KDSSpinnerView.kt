package com.vandenbreemen.kevindesignsystem.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/**
 *
 * @author kevin
 */
class KDSSpinnerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val model = SpinnerSegmentsModel()

    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10.0f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val masterRectangle = Rect(
            0, 0, (width), height
        )

        model.draw(masterRectangle, canvas)

    }

}