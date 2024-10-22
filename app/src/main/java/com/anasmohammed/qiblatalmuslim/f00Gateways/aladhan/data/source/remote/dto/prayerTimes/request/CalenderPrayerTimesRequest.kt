package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.request

import androidx.annotation.Keep

@Keep
data class CalenderPrayerTimesRequest(
    var month: String,
    var year: String,
    var latitude: Double,
    var longitude: Double,
    var day: String="",
    var method: Int = 5 // egypt
) {
    val isAllDataCollected
        get() = month.isNotEmpty() && year.isNotEmpty() && latitude != 0.0 && longitude != 0.0
}