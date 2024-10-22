package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GeometryDto(
    val bounds: BoundsDto,
    val location: LocationDto,
    @SerializedName("location_type")
    val locationType: String,
    val viewport: ViewportDto
)