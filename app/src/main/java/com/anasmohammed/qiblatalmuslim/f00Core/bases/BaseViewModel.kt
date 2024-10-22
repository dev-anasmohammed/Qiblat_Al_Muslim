package com.anasmohammed.qiblatalmuslim.f00Core.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> executeCallAndHandleResult(
        call: suspend () -> DomainResult<T>,
        doAfterSuccess: ((T?) -> Unit)? = null,
        updateState: MutableStateFlow<UiState<T>>
    ) : Job {
        return viewModelScope.launch {
            updateState.value = UiState.Loading()
            when (val resultState = call()) {
                is DomainResult.Error -> {
                    updateState.value = UiState.Error(resultState.message)
                }

                is DomainResult.Success -> {
                    updateState.value = UiState.Success(resultState.data)
                    doAfterSuccess?.invoke(resultState.data)
                }

                is DomainResult.UnKnownError -> {
                    updateState.value = UiState.UnKnownError()
                }

                is DomainResult.UnAuthorized -> {
                    updateState.value = UiState.UnAuthorized()
                }

                is DomainResult.Nothing -> {
                    updateState.value = UiState.Idle()
                }

                is DomainResult.NetworkError -> {
                    updateState.value = UiState.NetworkError()
                }
            }
        }
    }
}
