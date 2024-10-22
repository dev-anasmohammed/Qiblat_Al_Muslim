package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.repositoryImpl

import com.anasmohammed.qiblatalmuslim.f00Core.network.handlers.globalNetworkCall
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.mapper.toPrayerTime
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.dao.PrayerTimeDao
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api.PrayerTimesApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.request.CalenderPrayerTimesRequest
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.PrayerTime
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.PrayerTimesRepository

class PrayerTimesRepositoryImpl(
    private val prayerTimeDao: PrayerTimeDao,
    private val prayerTimesApiService: PrayerTimesApiService
) : PrayerTimesRepository {
    override suspend fun getCalendarPrayerTimes(request: CalenderPrayerTimesRequest): DomainResult<List<PrayerTime>> {
        return globalNetworkCall(action = {
            prayerTimesApiService.getCalendarPrayerTimes(
                year = request.year,
                month = request.month,
                latitude = request.latitude,
                longitude = request.longitude,
                method = request.method
            )
        }, mapper = { item ->
            item?.data?.map { it.toPrayerTime() }?.flatten() ?: listOf()
        })
    }
}