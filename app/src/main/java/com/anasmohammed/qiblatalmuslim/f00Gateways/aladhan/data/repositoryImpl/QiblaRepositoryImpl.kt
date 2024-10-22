package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.repositoryImpl

import com.anasmohammed.qiblatalmuslim.f00Core.network.handlers.globalNetworkCall
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.mapper.toDomain
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api.QiblaApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.qibla.QiblaResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.QiblaRepository

class QiblaRepositoryImpl(private val qiblaApiService: QiblaApiService) : QiblaRepository {
    override suspend fun getQiblaDirection(
        latitude: Double,
        longitude: Double
    ): DomainResult<QiblaResponse> {
         return globalNetworkCall(
            action = { qiblaApiService.getQiblaDirection(latitude, longitude) },
            mapper = { it?.data.toDomain() }
        )
    }
}
