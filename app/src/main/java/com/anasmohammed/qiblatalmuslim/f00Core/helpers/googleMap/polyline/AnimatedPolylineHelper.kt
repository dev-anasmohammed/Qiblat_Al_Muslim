package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.StrokeStyle
import com.google.android.gms.maps.model.StyleSpan

fun GoogleMap.createAnimatedPolyLine(
    context: Context,
    points: List<LatLng>,
    lineColor: Int,
    isGradient: Boolean,
    lineWidth: Float,
    makeItOnce: Boolean = false,
    isLineDotted: Boolean = false ,
    doOnOnceDrawing: (() -> Unit)? = null
): AnimatedPolyline {
    //options
    val polylineOptions = PolylineOptions().width(lineWidth)

    var spanStyle: StyleSpan? = null

    if (isGradient) {
        val dot: PatternItem = Dot()
        val dottedPattern = listOf(dot)
        if (isLineDotted) polylineOptions.pattern(dottedPattern).geodesic(true)

        spanStyle = StyleSpan(
            StrokeStyle.gradientBuilder(
                Color.parseColor("#FAD69D"),
                Color.parseColor("#FF000000")
            ).build()
        )
    }

    if (!isGradient) {
        polylineOptions.color(ContextCompat.getColor(context, lineColor))
    }

    var animatedPolyline: AnimatedPolyline? = null

    animatedPolyline = AnimatedPolyline(
        map = this,
        points = points,
        polylineOptions = polylineOptions,
        interpolator = DecelerateInterpolator(),
        animatorListenerAdapter = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animatedPolyline?.start() // endless animation
            }
        },
        makeItOnce = makeItOnce,
        doOnOnceDrawing = doOnOnceDrawing,
        spanStyle = spanStyle
    )

    return animatedPolyline
}