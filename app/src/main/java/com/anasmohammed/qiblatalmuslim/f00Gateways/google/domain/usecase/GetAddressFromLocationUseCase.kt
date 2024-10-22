package com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.usecase

import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.repository.GoogleGeocodeRepository

class GetAddressFromLocationUseCase(private val googleGeocodeRepository: GoogleGeocodeRepository) {
    suspend operator fun invoke(
        latLng: String,
        language: String,
        key: String,
    ) = googleGeocodeRepository.getAddressFromLocation(latLng, language, key)
}