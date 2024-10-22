package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResultDto(
    @SerializedName("address_components")
    val addressComponents: List<AddressComponentDto>,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    val geometry: GeometryDto,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("plus_code")
    val plusCode: PlusCodeDto,
    val types: List<String>
)