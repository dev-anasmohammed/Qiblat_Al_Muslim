package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker.MapMarkerRippleHelper
import com.google.android.gms.maps.GoogleMap

fun GoogleMap.setMapCameraListeners(
    kaabaMarkerRippleHelper: MapMarkerRippleHelper
) {
    setOnCameraMoveStartedListener {
        kaabaMarkerRippleHelper.stopRipple()
    }

    setOnCameraMoveListener {
        kaabaMarkerRippleHelper.stopRipple()
    }

    setOnCameraIdleListener {
        kaabaMarkerRippleHelper.updateUserRippleMarker(this)
        kaabaMarkerRippleHelper.startRipple()
    }
}