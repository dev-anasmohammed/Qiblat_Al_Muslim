package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import android.content.Context
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker.setMarkerOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

fun GoogleMap.addMapMarker(context : Context, location: LatLng, iconDrawable: Int): Marker? {
    //options
    val markerOptions = setMarkerOptions(context, location, iconDrawable)

    //add marker
    return addMarker(markerOptions)
}
