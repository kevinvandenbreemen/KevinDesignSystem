package com.vandenbreemen.kevindesignsystem

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.vandenbreemen.kevindesignsystem.views.SpinnerSegment
import com.vandenbreemen.kevindesignsystem.views.SpinnerSegmentStyles
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun printDebug(message: String) {

        val messageView = TextView(this)
        messageView.text = message

        debugArea.addView(messageView)
    }

    override fun onResume() {
        super.onResume()

        val animator = ValueAnimator.ofFloat(0f, 3600f)
        val secondAnimator = ValueAnimator.ofFloat(0f, 360f)

        val spinnerModel = kevinSpinner.model

        val segment1 = SpinnerSegment(
            0.9f, 0f, 180f, SpinnerSegmentStyles.white
        )
        val segment2 = SpinnerSegment(
            0.80f,0f, 180f, SpinnerSegmentStyles.white
        )

        val segment3 = SpinnerSegment(
            0.9f, 0f, 90f, SpinnerSegmentStyles.white
        )
        nestedSpinner.model.addSegment(segment3)

        spinnerModel.apply {
            addSegment(segment2)
            addSegment(segment1)
        }

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            segment1.setOffset(value)

        }

        animator.addUpdateListener {
            val value = it.animatedValue as Float

            val toUse = value * 1.5

            segment2.setOffset(toUse.toFloat())

        }

        animator.addUpdateListener {
            kevinSpinner.invalidate()
        }

        secondAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            segment3.setOffset(-value)
            nestedSpinner.invalidate()
        }

        animator.interpolator = LinearInterpolator()
        animator.duration = 10000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()

        secondAnimator.interpolator = AccelerateDecelerateInterpolator()
        secondAnimator.duration = 1000
        secondAnimator.repeatCount = ValueAnimator.INFINITE
        secondAnimator.start()

        buildSuperSpinner()
    }

    private fun buildSuperSpinner() {
        val segment = SpinnerSegment(0.99f, 25f, 200f, SpinnerSegmentStyles.white)

        superSpinner.model.addSegment(segment)

        addSuperSpinnerAnimator(segment, 0f)

    }

    private fun addSuperSpinnerAnimator(segment: SpinnerSegment, currentOffset: Float) {
        val animator = ValueAnimator.ofFloat(25f)

        var finalOffset: Float = currentOffset

        animator.doOnEnd {
            addSuperSpinnerAnimator(segment, finalOffset)
        }

        animator.apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500

            addUpdateListener {
                val value = it.animatedValue as Float
                val offset = currentOffset + value
                finalOffset = offset
                segment.setOffset(offset)

                superSpinner.invalidate()
            }


        }

        animator.start()


    }
}
