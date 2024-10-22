package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.qibla

import androidx.annotation.Keep

@Keep
data class QiblaResponseDto(
    val direction: Double?,
    val latitude: Double?,
    val longitude: Double?
)