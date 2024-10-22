package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddressComponentDto(
    @SerializedName("long_name")
    val longName: String?,
    @SerializedName("short_name")
    val shortName: String?,
    val types: List<String>?
)