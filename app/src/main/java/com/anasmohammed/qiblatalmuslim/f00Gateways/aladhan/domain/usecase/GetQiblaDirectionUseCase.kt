package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase

import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.QiblaRepository

class GetQiblaDirectionUseCase(private val qiblaRepository: QiblaRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double) =
        qiblaRepository.getQiblaDirection(latitude, longitude)
}