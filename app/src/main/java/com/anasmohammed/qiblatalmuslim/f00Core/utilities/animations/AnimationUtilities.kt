package com.anasmohammed.qiblatalmuslim.f00Core.utilities.animations

import android.view.View

fun View.animateAlpha(
    startAlpha: Float,
    endAlpha: Float,
    duration: Long,
    startVisibility: Int? = null
) {
    alpha = startAlpha
    if (startVisibility != null) this.visibility = startVisibility
    animate().alpha(endAlpha).duration = duration
}

fun View.animateUpAndDown(startDistance: Float, endDistance: Float, duration: Long) {
    this.translationY = startDistance
    animate().translationY(endDistance).duration = duration
}


