package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.date

import androidx.annotation.Keep

@Keep
data class PrayersDateDto(
    val gregorian: GregorianDto?,
    val hijri: HijriDto?,
    val readable: String?,
    val timestamp: String?
)