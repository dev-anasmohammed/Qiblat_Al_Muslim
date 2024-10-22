package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker

import android.content.Context
import androidx.core.content.ContextCompat
import com.anasmohammed.qiblatalmuslim.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlin.math.pow

class MapMarkerRippleHelper(private val context: Context) {

    private var userRippleMarker: MapRippleMarker? = null
    private var oldZoom: Float = 0f

    fun stopRipple() {
        userRippleMarker?.stopRippleMapAnimation()
    }

    fun addUserRippleMarker(googleMap: GoogleMap, latLng: LatLng) {
        userRippleMarker = MapRippleMarker(
            mGoogleMap = googleMap,
            mLatLng = latLng,
            context = context
        )
        userRippleMarker?.withNumberOfRipples(3)
        userRippleMarker?.withFillColor(
            ContextCompat.getColor(
                context, R.color.ripple_transparent_color
            )
        )
        userRippleMarker?.withStrokeColor(
            ContextCompat.getColor(
                context,
                android.R.color.transparent
            )
        )
        userRippleMarker?.withTransparency(0.1f)
        userRippleMarker?.withRippleDuration(1000)
        userRippleMarker?.startRippleMapAnimation()
    }

    fun startRipple(){
        userRippleMarker?.startRippleMapAnimation()
    }
    fun updateUserRippleMarker(googleMap: GoogleMap) {
        val currentZoom = googleMap.cameraPosition.zoom

        if (currentZoom == oldZoom) return

        //remove old ripple
        userRippleMarker?.stopRippleMapAnimation()

        oldZoom = currentZoom
        userRippleMarker?.mDistance?.value = calculateRadius(currentZoom).toDouble()
        userRippleMarker?.startRippleMapAnimation()
    }

    private fun calculateRadius(zoom: Float): Float {
        val baseRadius = 11000.0
        val zoomLevelMultiplier = 2.0.pow(10.0 - zoom)
        return (baseRadius * zoomLevelMultiplier).toFloat()
    }

}

