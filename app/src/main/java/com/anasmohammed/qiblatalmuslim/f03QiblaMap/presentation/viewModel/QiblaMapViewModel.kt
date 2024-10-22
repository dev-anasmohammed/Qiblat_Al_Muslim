package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.viewModel

import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseViewModel
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.UiState
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.qibla.QiblaResponse
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase.GetQiblaDirectionUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QiblaMapViewModel @Inject constructor(
    private val getQiblaDirectionUseCase: GetQiblaDirectionUseCase,
) : BaseViewModel() {

    val kaabaLocation = MutableStateFlow(LatLng(21.422541738411454, 39.82619186868779))
    val userLocation = MutableStateFlow(LatLng(0.0, 0.0))

    val isIntroWorking = MutableStateFlow(true)

    val userPhoneDirection = MutableStateFlow(0f)
    val qiblaDirection = MutableStateFlow(0f)

    private val _qiblaDirectionResponse =
        MutableStateFlow<UiState<QiblaResponse>>(UiState.Idle())
    val qiblaDirectionResponse = _qiblaDirectionResponse.asStateFlow()

    fun getQiblaDirections(latitude: Double, longitude: Double) {
        executeCallAndHandleResult(
            call = { getQiblaDirectionUseCase.invoke(latitude, longitude) },
            updateState = _qiblaDirectionResponse
        )
    }
}
