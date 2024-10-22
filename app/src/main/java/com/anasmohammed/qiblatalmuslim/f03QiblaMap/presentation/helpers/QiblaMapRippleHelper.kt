package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker.MapMarkerRippleHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

fun GoogleMap.startRippleMarker(
    kaabaMarkerRippleHelper: MapMarkerRippleHelper,
    kaabaLocation: LatLng
) {
    kaabaMarkerRippleHelper.addUserRippleMarker(this, kaabaLocation)
    kaabaMarkerRippleHelper.updateUserRippleMarker(this)
    kaabaMarkerRippleHelper.startRipple()
}