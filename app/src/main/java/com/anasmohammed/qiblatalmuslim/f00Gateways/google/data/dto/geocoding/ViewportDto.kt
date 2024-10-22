package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding

import androidx.annotation.Keep

@Keep
data class ViewportDto(
    val northeast: LocationDto,
    val southwest: LocationDto
)