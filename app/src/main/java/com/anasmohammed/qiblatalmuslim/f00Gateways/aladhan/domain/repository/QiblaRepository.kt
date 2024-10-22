package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.qibla.QiblaResponse

interface QiblaRepository {
    suspend fun getQiblaDirection(latitude: Double, longitude: Double): DomainResult<QiblaResponse>
}
