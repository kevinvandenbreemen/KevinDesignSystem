package com.vandenbreemen.kevindesignsystem.views

import android.animation.Animator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.vandenbreemen.kevindesignsystem.R
import kotlinx.android.synthetic.main.layout_eye_of_logs.*
import java.util.*

/**
 * Inherit from this activity to create a UI for system type screens.  Includes a spinner in the background
 */
class KDSSystemActivity : AppCompatActivity() {

    private val animatorsList: MutableList<Animator> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k_d_s_system)

        buildSpinnerView(outermostSpinner)
        buildSpinnerView(spinner1Of4)
        buildSpinnerView(spinner2Of4)
        buildSpinnerView(spinner3Of4)
        buildSpinnerView(spinner4Of4)
        buildSpinnerView(innerMostSpinner)
    }

    override fun onPause() {
        super.onPause()

        animatorsList.forEach { anim ->
            anim.pause()
        }

    }

    override fun onResume() {
        super.onResume()

        animatorsList.forEach { anim->
            anim.resume()
        }

    }

    private fun buildSpinnerView(spinner: KDSSpinnerView) {
        buildSpinnerWheel(spinner, buildSegments = { spinner ->

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
                initialOffsets[seg] = (seg.currentAngle - seg.startAngle)
            }

            animator.apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = (1000 + random.nextInt(1000)).toLong()
            }

            segments.forEach {segment ->
                var multiplier = random.nextFloat()
                if(random.nextBoolean()) {
                    multiplier *= -1
                }

                initialOffsets[segment]?.let {currentOffset ->

                    animator.apply {
                        addUpdateListener {
                            var value = (it.animatedValue as Float) * multiplier
                            val offset = currentOffset + value
                            segment.setOffset(offset)
                        }
                    }

                }


            }

            animator.addUpdateListener {
                spinner.invalidate()
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
        animator.doOnEnd {
            animatorsList.remove(animator)
            buildAndStartAnimator(segments, buildAnimator)
        }
        animatorsList.add(animator)
        animator.start()
    }
}
