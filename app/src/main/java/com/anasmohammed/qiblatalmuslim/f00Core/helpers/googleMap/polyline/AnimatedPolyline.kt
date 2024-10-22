package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline

import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline.PolylineOptionsExtensions.copyPolylineOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.StyleSpan

class AnimatedPolyline(
    private var map: GoogleMap,
    private var points: List<LatLng>,
    private var polylineOptions: PolylineOptions,
    private var spanStyle: StyleSpan? = null,
    interpolator: TimeInterpolator? = null,
    private val animatorListenerAdapter: AnimatorListenerAdapter? = null,
    private val makeItOnce: Boolean = false,
    private val doOnOnceDrawing: (() -> Unit)? = null
) : ValueAnimator.AnimatorUpdateListener {

    private var renderedPolyline: Polyline? = null
    private lateinit var legs: List<Double>
    private var totalPathDistance: Double = 0.0
    private var animator: ValueAnimator = ValueAnimator.ofFloat(0f, 100f)

    init {
        animator.duration = 3000
        interpolator?.let {
            animator.interpolator = it
        }
        animator.addUpdateListener(this)
        animatorListenerAdapter?.let {
            animator.addListener(it)
        }
    }

    fun start() {
        legs = CalculationHelper.calculateLegsLengths(points)
        totalPathDistance = legs.sum()

        animatorListenerAdapter?.let {
            if (animator.listeners == null || !animator.listeners.contains(it)) {
                animator.duration = getDuration(totalPathDistance)
                animator.addListener(it)
                animator.addUpdateListener(this)
            }
        }
        animator.start()
    }

    private fun getDuration(totalPathDistance: Double): Long {
        return when (totalPathDistance) {
            in 0.0..1000.0 -> 1000L
            in 1000.0..3000.0 -> 3000L
            in 3000.0..5000.0 -> 6000L
            in 5000.0..8000.0 -> 9000L
            in 8000.0..11000.0 -> 12000L
            else -> 3000L
        }
    }

    fun remove(clearPolyline: Boolean = true) {
        animator.removeUpdateListener(this)
        animatorListenerAdapter?.let {
            animator.removeListener(it)
        }
        animator.cancel()
        if (clearPolyline) renderedPolyline?.remove()
    }

    private fun renderPolylineOnMap(polylineOptions: PolylineOptions) {
        val newPolyline = map.addPolyline(polylineOptions)
        renderedPolyline?.remove()
        renderedPolyline = newPolyline
    }

    override fun onAnimationUpdate(p0: ValueAnimator) {
        val fraction = animator.animatedValue as Float
        val pathSection = totalPathDistance * fraction / 100
        renderPolylineOnMap(
            CalculationHelper.polylineUntilSection(
                points, legs, pathSection, polylineOptions.copyPolylineOptions(spanStyle)
            )
        )

        if (makeItOnce && fraction == 100f) {
            remove(false)
            doOnOnceDrawing?.invoke()
            return
        }
    }
}