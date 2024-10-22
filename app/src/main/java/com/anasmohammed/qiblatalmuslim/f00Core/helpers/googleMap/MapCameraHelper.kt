package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/** Move **/
fun GoogleMap.moveMapCamera(target: LatLng, zoom: Float = 16f) {
    moveCamera(CameraUpdateFactory.newLatLngZoom(target, zoom))
}

fun GoogleMap.rotateCamera(bearing: Float) {
    val cameraPosition =
        CameraPosition.builder().target(cameraPosition.target) // Keep the current target
            .zoom(cameraPosition.zoom) // Keep the current zoom level
            .bearing(bearing) // Set the desired bearing
            .tilt(cameraPosition.tilt) // Keep the current tilt angle
            .build()

    animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null)
}

fun GoogleMap.animateCameraWithBounds(
    context: Context,
    boundsPlaces: List<LatLng?>,
    durationInMs: Int = 1000,
    isUserTouchesEnabled: Boolean = false,
    leftPadding: Int = 50,
    topPadding: Int = 50,
    rightPadding: Int = 50,
    bottomPadding: Int = 300,
    enableMapPadding: Boolean = false,
    mapPaddingLeft: Int = 0,
    mapPaddingTop: Int = 0,
    mapPaddingRight: Int = 0,
    mapPaddingBottom: Int = 0,
    onCancelAnimate: (() -> Unit)? = null,
    onFinishAnimate: (() -> Unit)? = null,
) {

    //enable / disable touches
    uiSettings.setAllGesturesEnabled(isUserTouchesEnabled)

    //bounds
    val bounds = LatLngBounds.Builder()
    boundsPlaces.forEach { place ->
        if (place != null) {
            bounds.include(place)
        }
    }

    // Calculate the camera update with padding only from the bottom
    val displayMetrics = context.resources.displayMetrics
    val totalPaddingHorizontal = (leftPadding + rightPadding)
    val totalPaddingVertical = (topPadding + bottomPadding)

    //camera
    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
        bounds.build(),
        displayMetrics.widthPixels - totalPaddingHorizontal,
        displayMetrics.heightPixels - totalPaddingVertical,
        bottomPadding
    )

    //padding if needed
    if (enableMapPadding) setPadding(
        mapPaddingLeft, mapPaddingTop, mapPaddingRight, mapPaddingBottom
    )

    animateCamera(cameraUpdate, durationInMs, object : GoogleMap.CancelableCallback {
        override fun onCancel() {
            uiSettings.setAllGesturesEnabled(true)
            onCancelAnimate?.invoke()
        }

        override fun onFinish() {
            uiSettings.setAllGesturesEnabled(true)
            onFinishAnimate?.invoke()
        }
    })
}