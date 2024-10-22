package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.mapper

import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding.AddressComponentDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding.AddressFromLocationResponseDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding.ResultDto
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressComponent
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressFromLocationResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.Result

fun AddressFromLocationResponseDto?.toDomain(): AddressFromLocationResponse {
    return AddressFromLocationResponse(
        results = this?.results?.map { it.toDomain() }
    )
}

fun ResultDto?.toDomain(): Result {
    return Result(
        addressComponents = this?.addressComponents?.map { it.toDomain() },
        formattedAddress = this?.formattedAddress,
        placeId = this?.placeId,
        types = this?.types
    )
}

fun AddressComponentDto?.toDomain(): AddressComponent {
    return AddressComponent(
        longName = this?.longName,
        shortName = this?.shortName,
        types = this?.types
    )
}