package com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.repository

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressFromLocationResponse

interface GoogleGeocodeRepository {
    suspend fun getAddressFromLocation(
        latLng: String,
        language: String,
        key: String,
    ): DomainResult<AddressFromLocationResponse>
}
