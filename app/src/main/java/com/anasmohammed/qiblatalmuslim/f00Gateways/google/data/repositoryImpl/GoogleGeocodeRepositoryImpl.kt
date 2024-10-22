package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.repositoryImpl

import com.anasmohammed.qiblatalmuslim.f00Core.network.handlers.globalNetworkCall
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.mapper.toDomain
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.source.remote.api.GoogleGeocodeApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressFromLocationResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.repository.GoogleGeocodeRepository

class GoogleGeocodeRepositoryImpl(private val googleApiService: GoogleGeocodeApiService) : GoogleGeocodeRepository {
    override suspend fun getAddressFromLocation(
        latLng: String,
        language: String,
        key: String
    ): DomainResult<AddressFromLocationResponse> {
        return globalNetworkCall(
            action = {googleApiService.getAddressFromLocation(latLng, language, key)},
            mapper = {it.toDomain()}
        )
    }
}