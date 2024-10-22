package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.StyleSpan

object PolylineOptionsExtensions {

    fun PolylineOptions.toPolylineOptions(
        points: List<LatLng>,
        spanStyle: StyleSpan?
    ): PolylineOptions {
        val polylineOptions = this.copyPolylineOptions(spanStyle)
        polylineOptions.addAll(points)
        return polylineOptions
    }

    fun PolylineOptions.copyPolylineOptions(spanStyle: StyleSpan?): PolylineOptions {
        val polylineOptions = PolylineOptions()
        polylineOptions
            .color(this.color)
            .width(this.width)
            .startCap(this.startCap)
            .endCap(this.endCap)
            .clickable(this.isClickable)
            .jointType(this.jointType)
            .visible(this.isVisible)
            .pattern(this.pattern)

        if (spanStyle != null) polylineOptions.addSpan(spanStyle)

        return polylineOptions
    }
}