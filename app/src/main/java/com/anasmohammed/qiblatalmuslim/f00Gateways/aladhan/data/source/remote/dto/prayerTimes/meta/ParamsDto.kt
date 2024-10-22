package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.meta

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ParamsDto(
    @SerializedName("Fajr")
    val fajr: Double?,
    @SerializedName("Isha")
    val isha: Double?
)