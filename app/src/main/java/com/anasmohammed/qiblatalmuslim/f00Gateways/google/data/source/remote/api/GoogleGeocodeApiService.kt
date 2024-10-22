package com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.source.remote.api

import com.anasmohammed.qiblatalmuslim.f00Gateways.google.Constants.GOOGLE_BASE_URL
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.dto.geocoding.AddressFromLocationResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleGeocodeApiService {
    @GET("$GOOGLE_BASE_URL/geocode/json")
    suspend fun getAddressFromLocation(
        @Query("latlng") latLng: String,
        @Query("language") language: String,
        @Query("key") key: String,
        @Query("result_type") resultType: String= "route",
    ): Response<AddressFromLocationResponseDto>
}
