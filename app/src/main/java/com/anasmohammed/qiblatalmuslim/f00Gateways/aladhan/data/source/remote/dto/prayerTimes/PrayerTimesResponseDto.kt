package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes

import androidx.annotation.Keep
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.date.PrayersDateDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.meta.MetaDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.timings.PrayersTimingsDto
import com.google.gson.annotations.SerializedName

@Keep
data class PrayerTimesResponseDto(
    @SerializedName("date")
    val prayersDate: PrayersDateDto?,
    @SerializedName("meta")
    val meta: MetaDto?,
    @SerializedName("timings")
    val prayersTimings: PrayersTimingsDto?
)