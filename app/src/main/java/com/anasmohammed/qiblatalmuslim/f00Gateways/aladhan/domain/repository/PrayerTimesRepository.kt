package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.request.CalenderPrayerTimesRequest
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.PrayerTime

interface PrayerTimesRepository {
    suspend fun getCalendarPrayerTimes(request: CalenderPrayerTimesRequest): DomainResult<List<PrayerTime>>
}
