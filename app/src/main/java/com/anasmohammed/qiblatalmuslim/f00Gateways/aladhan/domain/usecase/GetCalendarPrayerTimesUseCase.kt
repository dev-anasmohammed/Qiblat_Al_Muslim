package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase

import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.request.CalenderPrayerTimesRequest
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.PrayerTimesRepository

class GetCalendarPrayerTimesUseCase(private val prayerTimesRepository: PrayerTimesRepository) {
    suspend operator fun invoke(request: CalenderPrayerTimesRequest) =
        prayerTimesRepository.getCalendarPrayerTimes(request)
}