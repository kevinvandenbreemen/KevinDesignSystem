package com.vandenbreemen.kevindesignsystem

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
import com.vandenbreemen.kevindesignsystem.views.SpinnerSegment
import com.vandenbreemen.kevindesignsystem.views.SpinnerSegmentStyles
import com.vandenbreemen.kevindesignsystem.views.SpinnerSegmentsModel
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
        val otherAnimator = ValueAnimator.ofFloat(0f, 3600f)

        val spinnerModel = kevinSpinner.model
        val segment1 = SpinnerSegment(
            0.9f, 0f, 180f, SpinnerSegmentStyles.white
        )
        val segment2 = SpinnerSegment(
            0.75f,0f, 180f, SpinnerSegmentStyles.white
        )

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

        animator.interpolator = LinearInterpolator()
        animator.duration = 10000
        animator.start()


    }
}
