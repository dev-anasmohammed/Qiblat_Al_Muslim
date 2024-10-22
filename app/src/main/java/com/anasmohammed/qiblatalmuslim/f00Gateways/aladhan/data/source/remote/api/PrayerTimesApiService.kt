package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.BaseApiResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.Constants.ALADHAN_BASE_URL
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.PrayerTimesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesApiService {
    @GET("$ALADHAN_BASE_URL/calendar/{year}/{month}")
    suspend fun getCalendarPrayerTimes(
        @Path("year") year: String,
        @Path("month") month: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int ,
    ): Response<BaseApiResponse<List<PrayerTimesResponseDto>>>
}