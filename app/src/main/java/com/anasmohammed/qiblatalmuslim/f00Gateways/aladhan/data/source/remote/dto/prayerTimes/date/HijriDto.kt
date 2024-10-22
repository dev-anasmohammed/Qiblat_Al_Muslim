package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.date

import androidx.annotation.Keep
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.prayerTimes.date.Designation

@Keep
data class HijriDto(
    val date: String?,
    val day: String?,
    val designation: Designation?,
    val format: String?,
    val holidays: List<Any?>?,
    val month: MonthDto?,
    val weekday: WeekdayDto?,
    val year: String?
)