package com.vandenbreemen.kevindesignsystem

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
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
        animator.addUpdateListener {

            val value = it.animatedValue as Float

            kevinSpinner.setAngleOffset(value)

            printDebug("curStart=${kevinSpinner.currentStart}")
        }

        animator.interpolator = LinearInterpolator()
        animator.duration = 10000
        animator.start()

    }
}
