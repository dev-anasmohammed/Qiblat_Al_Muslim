package com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model

data class Result(
    val addressComponents: List<AddressComponent>?,
    val formattedAddress: String?,
    val placeId: String?,
    val types: List<String>?
)