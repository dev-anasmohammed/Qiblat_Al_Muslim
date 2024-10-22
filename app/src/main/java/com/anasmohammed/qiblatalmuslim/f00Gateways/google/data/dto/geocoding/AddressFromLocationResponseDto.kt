package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddressFromLocationResponseDto(
    @SerializedName("plus_code")
    val plusCode: PlusCodeDto?,
    val results: List<ResultDto>?
)