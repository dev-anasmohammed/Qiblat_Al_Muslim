package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.meta

import androidx.annotation.Keep

@Keep
data class MetaDto(
    val latitude: Double?,
    val latitudeAdjustmentMethod: String?,
    val longitude: Double?,
    val method: MethodDto?,
    val midnightMode: String?,
    val offset: OffsetDto?,
    val school: String?,
    val timezone: String?
)