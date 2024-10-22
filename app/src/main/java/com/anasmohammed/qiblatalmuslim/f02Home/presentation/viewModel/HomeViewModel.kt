package com.anasmohammed.qiblatalmuslim.f02Home.presentation.viewModel

import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseViewModel
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.UiState
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.dto.prayerTimes.request.CalenderPrayerTimesRequest
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.PrayerTime
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase.GetCalendarPrayerTimesUseCase
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressFromLocationResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.usecase.GetAddressFromLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCalendarPrayerTimesUseCase: GetCalendarPrayerTimesUseCase,
    private val getAddressFromLocationUseCase: GetAddressFromLocationUseCase
) : BaseViewModel() {

    private val _calendarPrayerTimesResponse =
        MutableStateFlow<UiState<List<PrayerTime>>>(UiState.Idle())
    val calendarPrayerTimesResponse = _calendarPrayerTimesResponse.asStateFlow()

    private val _getAddressFromLocationResponse =
        MutableStateFlow<UiState<AddressFromLocationResponse>>(UiState.Idle())
    val getAddressFromLocationResponse = _getAddressFromLocationResponse.asStateFlow()

    val calenderPrayerTimesRequest = MutableStateFlow(CalenderPrayerTimesRequest("", "", 0.0, 0.0))
    val todayPrayerTimes = MutableStateFlow<MutableList<PrayerTime>>(mutableListOf())
    val animateNextPrayer = MutableStateFlow(true)

    fun getCalenderPrayerTimes() {
        executeCallAndHandleResult(
            call = { getCalendarPrayerTimesUseCase.invoke(calenderPrayerTimesRequest.value) },
            updateState = _calendarPrayerTimesResponse
        )
    }

    fun getAddressFromLocation(latLng: String, language: String, key: String) {
        executeCallAndHandleResult(
            call = { getAddressFromLocationUseCase.invoke(latLng, language, key) },
            updateState = _getAddressFromLocationResponse
        )
    }
}
