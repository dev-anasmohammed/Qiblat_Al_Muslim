package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.prayerTimes.date

data class PrayersDate(
    val gregorian: Gregorian?,
    val hijri: Hijri?,
    val readable: String?,
    val timestamp: String?
)