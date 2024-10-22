package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.prayerTimes.date

data class Hijri(
    val date: String?,
    val day: String?,
    val designation: Designation?,
    val format: String?,
    val holidays: List<Any?>?,
    val month: Month?,
    val weekday: Weekday?,
    val year: String?
)