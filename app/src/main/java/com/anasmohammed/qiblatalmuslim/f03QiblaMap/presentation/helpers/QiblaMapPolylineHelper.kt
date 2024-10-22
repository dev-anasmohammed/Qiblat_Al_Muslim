package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline.AnimatedPolyline
import kotlinx.coroutines.delay

suspend fun startAnimatePolyline(
    backgroundPolyline: AnimatedPolyline?,
    foregroundPolyline: AnimatedPolyline?
) {
    backgroundPolyline?.start()
    delay(2500)
    foregroundPolyline?.start()
}