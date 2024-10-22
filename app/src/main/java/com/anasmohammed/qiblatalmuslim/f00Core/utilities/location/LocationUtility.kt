package com.anasmohammed.qiblatalmuslim.f00Core.utilities.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class LocationUtility {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context , callback: LocationResultCallback) {
        try {
            val result = LocationServices.getFusedLocationProviderClient(context).getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()
            callback.onLocationRetrieved(result)
        } catch (e: Exception) {
            callback.onLocationFetchFailed(e)
        }
    }
}