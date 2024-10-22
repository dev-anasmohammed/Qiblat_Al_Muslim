package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.BaseApiResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.Constants.ALADHAN_BASE_URL
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.qibla.QiblaResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QiblaApiService {
    @GET("$ALADHAN_BASE_URL/qibla/{latitude}/{longitude}")
    suspend fun getQiblaDirection(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Response<BaseApiResponse<QiblaResponseDto>>
}