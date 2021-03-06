package com.vandenbreemen.kevindesignsystem.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.vandenbreemen.kevindesignsystem.R

/**
 * Default segment styles for drawing the spinner segments
 */
class SpinnerSegmentStyles(private val context: Context) {

    val white: Paint
    val thinWhite: Paint
    val red: Paint
    val thickRed: Paint
    val green: Paint
    val blue: Paint

    init {
        white = Paint().apply {
            color = context.resources.getColor(R.color.kds_red, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        thinWhite = Paint().apply {
            color = context.resources.getColor(R.color.kds_light, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 7f
        }

        red = Paint().apply {
            color = context.resources.getColor(R.color.kds_red, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        thickRed = Paint().apply {
            color = context.resources.getColor(R.color.kds_red, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 50f
        }

        green = Paint().apply {
            color = context.resources.getColor(R.color.kds_green, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 15f
        }

        blue = Paint().apply {
            color = context.resources.getColor(R.color.kds_blue, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = 15f
        }

    }



}

/**
 * @param   radiusMultiplier    Factor to multiply the drawing area radius by to compute the radius of this arc with respect
 * to the size of the rect it's drawn in.  Value between 0 and 1
 */
class SpinnerSegment(val radiusMultiplier: Float, val startAngle: Float, private val sweepAngle: Float,
                     private val paint: Paint) {

    var currentAngle = startAngle

    /**
     * Sets the current angle to draw this segment from to the startAngle + the @param offset
     */
    fun setOffset(offset: Float) {
        currentAngle = startAngle + offset
    }

    /**
     * Takes care of drawing this segment on the given canvas
     * @param   rect    Rectangle in which to draw this
     * @param   canvas  Canvas to use for drawing
     */
    fun paint(rect: Rect, canvas: Canvas) {
        canvas.drawArc(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat(),
            currentAngle, sweepAngle, false, paint)
    }

}

/**
 * Representation of set of segments to be rendered on the spinner
 * @author kevin
 */
class SpinnerSegmentsModel {

    private var segments: MutableCollection<SpinnerSegment> = mutableListOf()

    /**
     * Draw all segments using the given rectangle
     */
    fun draw(rect: Rect, canvas: Canvas) {
        segments.forEach { segment ->

            var delta = (rect.right - rect.left).toFloat()
            delta *= segment.radiusMultiplier

            var difference = ((rect.right - rect.left) - delta).toInt()

            delta *= (segment.radiusMultiplier).toInt()

            val paintRect = Rect(
                difference, difference, rect.right-difference, rect.right-difference
            )

            segment.paint(paintRect, canvas)
        }
    }

    fun addSegment(segment: SpinnerSegment) {
        segments.add(segment)
    }


}