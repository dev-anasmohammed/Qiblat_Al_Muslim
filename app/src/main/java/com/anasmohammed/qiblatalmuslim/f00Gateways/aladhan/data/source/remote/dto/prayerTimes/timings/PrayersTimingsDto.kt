package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.timings

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PrayersTimingsDto(
    @SerializedName("Asr")
    val asr: String?,
    @SerializedName("Dhuhr")
    val dhuhr: String?,
    @SerializedName("Fajr")
    val fajr: String?,
    @SerializedName("Firstthird")
    val firstthird: String?,
    @SerializedName("Imsak")
    val imsak: String?,
    @SerializedName("Isha")
    val isha: String?,
    @SerializedName("Lastthird")
    val lastthird: String?,
    @SerializedName("Maghrib")
    val maghrib: String?,
    @SerializedName("Midnight")
    val midnight: String?,
    @SerializedName("Sunrise")
    val sunrise: String?,
    @SerializedName("Sunset")
    val sunset: String?
)