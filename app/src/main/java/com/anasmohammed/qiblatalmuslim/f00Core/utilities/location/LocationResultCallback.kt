package com.anasmohammed.qiblatalmuslim.f00Core.utilities.location

import android.location.Location

interface LocationResultCallback {
    fun onLocationRetrieved(locationInfo: Location)
    fun onLocationFetchFailed(exception: Exception)
}