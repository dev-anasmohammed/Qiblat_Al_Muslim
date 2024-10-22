package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions

fun Fragment.initGoogleMap(mapId: Int, callBack: OnMapReadyCallback) {
    val mapFragment = childFragmentManager.findFragmentById(mapId) as SupportMapFragment
    mapFragment.getMapAsync(callBack)
}

fun GoogleMap.setGoogleMapStyle(context: Context, styleResId: Int): Boolean {
    return setMapStyle(
        MapStyleOptions.loadRawResourceStyle(context, styleResId)
    )
}

fun getPointsBetweenTwoLatLng(firstLatLng: LatLng, secondLatLng: LatLng): List<LatLng> {
    val points = mutableListOf<LatLng>()

    for (i in 0..5) {
        val fraction = i.toDouble() / 5
        val lat = (secondLatLng.latitude - firstLatLng.latitude) * fraction + firstLatLng.latitude
        val lng =
            (secondLatLng.longitude - firstLatLng.longitude) * fraction + firstLatLng.longitude
        points.add(LatLng(lat, lng))
    }

    return points
}
