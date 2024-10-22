package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.mapper

import com.anasmohammed.qiblatalmuslim.R
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.time.formatTimeTo12Hour
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.PrayerTimesResponseDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.qibla.QiblaResponseDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.PrayerTime
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.qibla.QiblaResponse

/** Daily Prayer Times**/
fun PrayerTimesResponseDto?.toPrayerTime(): List<PrayerTime> {
    val gregorianDayDate = this?.prayersDate?.gregorian?.date ?: ""
    return listOf(
        PrayerTime(
            id = 0,
            gregorianDayDate = gregorianDayDate,
            name = "Fajr",
            time = formatTimeTo12Hour(this?.prayersTimings?.fajr?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_fajr
        ),
        PrayerTime(
            id = 1,
            gregorianDayDate = gregorianDayDate,
            name = "Shrook",
            time = formatTimeTo12Hour(this?.prayersTimings?.sunrise?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_shrook
        ),
        PrayerTime(
            id = 2,
            gregorianDayDate = gregorianDayDate,
            name = "Dhuhr",
            time = formatTimeTo12Hour(this?.prayersTimings?.dhuhr?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_dhuhr
        ),
        PrayerTime(
            id = 3,
            gregorianDayDate = gregorianDayDate,
            name = "Asr",
            time = formatTimeTo12Hour(this?.prayersTimings?.asr?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_asr
        ),
        PrayerTime(
            id = 4,
            gregorianDayDate = gregorianDayDate,
            name = "Maghrib",
            time = formatTimeTo12Hour(this?.prayersTimings?.maghrib?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_maghrib
        ),
        PrayerTime(
            id = 5,
            gregorianDayDate = gregorianDayDate,
            name = "Isha",
            time = formatTimeTo12Hour(this?.prayersTimings?.isha?.removeSuffix(" (EEST)")?.removeSuffix(" (EET)") ?: ""),
            iconRes = R.drawable.ic_isha
        )
    )
}

/** Qibla **/
fun QiblaResponseDto?.toDomain(): QiblaResponse {
    return QiblaResponse(
        direction = this?.direction ?: 0.0,
        latitude = this?.latitude ?: 0.0,
        longitude = this?.longitude ?: 0.0
    )
}