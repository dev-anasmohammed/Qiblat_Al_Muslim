package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.prayerTimes.date

data class Gregorian(
    val date: String?,
    val day: String?,
    val designation: Designation?,
    val format: String?,
    val month: Month?,
    val weekday: Weekday?,
    val year: String?
)