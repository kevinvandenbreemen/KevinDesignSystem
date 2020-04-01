package com.vandenbreemen.kevindesignsystem.views

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.vandenbreemen.kevindesignsystem.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_eye_of_logs.*
import java.util.*

/**
 * Inherit from this activity to create a UI for system type screens.  Includes a spinner in the background
 */
class KDSSystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k_d_s_system)

        buildSpinnerWheel(outermostSpinner, buildSegments = {spinner ->

            val segment1 = SpinnerSegment(0.9f, 20f, 100f, SpinnerSegmentStyles.white)
            val segment2 = SpinnerSegment(0.8f, 20f, 100f, SpinnerSegmentStyles.white)
            val segment3 = SpinnerSegment(0.7f, 20f, 100f, SpinnerSegmentStyles.white)

            spinner.model.addSegment(segment1)
            spinner.model.addSegment(segment2)
            spinner.model.addSegment(segment3)

            listOf(segment1, segment2, segment3)

        }, buildAnimator = {segments ->
            val random = Random(System.nanoTime())

            var value = 25f + (random.nextFloat() * 100)
            val animator = ValueAnimator.ofFloat(value)

            var initialOffsets: MutableMap<SpinnerSegment, Float> = mutableMapOf()
            segments.forEach { seg->
                initialOffsets[seg] = seg.currentAngle
            }

            animator.apply {

                interpolator = AccelerateDecelerateInterpolator()
                duration = (1000 + random.nextInt(500)).toLong()

                addUpdateListener {
                    segments.forEach { segment ->

                        var value = (it.animatedValue as Float)

                        initialOffsets[segment]?.let { currentOffset ->
                            val offset = currentOffset + value
                            segment.setOffset(offset)
                        }


                    }

                    outermostSpinner.invalidate()
                }

            }

            animator

        })

    }

    /**
     * Build a given spinner wheel with desired animator/segments etc
     */
    private fun buildSpinnerWheel(spinner: KDSSpinnerView,
                                  buildSegments: (spinner: KDSSpinnerView)->List<SpinnerSegment>,
                                  buildAnimator: (segments: List<SpinnerSegment>)->ValueAnimator) {

        val segments = buildSegments(spinner)

        buildAndStartAnimator(segments, buildAnimator)

    }

    private fun buildAndStartAnimator(segments: List<SpinnerSegment>,
                                      buildAnimator: (segments: List<SpinnerSegment>)->ValueAnimator) {
        val animator = buildAnimator(segments)
        animator.doOnEnd { buildAndStartAnimator(segments, buildAnimator) }
        animator.start()
    }
}
