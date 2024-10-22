package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import android.content.Context
import com.anasmohammed.qiblatalmuslim.R
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.setGoogleMapStyle
import com.google.android.gms.maps.GoogleMap

fun GoogleMap.setupMapPreferences(context: Context) {
    //style
    setGoogleMapStyle(context, R.raw.map_style)

    //ui
    uiSettings.isCompassEnabled = false
    uiSettings.isMapToolbarEnabled = false
}