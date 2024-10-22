package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.meta

import androidx.annotation.Keep

@Keep
data class MethodDto(
    val id: Int?,
    val location: LocationDto?,
    val name: String?,
    val params: ParamsDto?
)