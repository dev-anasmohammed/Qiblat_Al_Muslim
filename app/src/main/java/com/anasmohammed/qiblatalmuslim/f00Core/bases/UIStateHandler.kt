package com.anasmohammed.qiblatalmuslim.f00Core.bases

import com.anasmohammed.qiblatalmuslim.f00Core.network.model.ResultHelper
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.UiState
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.isError
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.isLoading
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.isSuccess

fun <T> handleUiState(
    uiState: UiState<T>,
    onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: ((T?) -> Unit)? = null,
    onSuccessSingleTime: ((T?) -> Unit)? = null,
    onError: ((message: String?) -> Unit)? = null,
    onErrorSingleTime: ((message: String?) -> Unit)? = null,
    onNetworkError: (() -> Unit)? = null,
    onUnKnownError: (() -> Unit)? = null,
    onUnAuthorized: (() -> Unit)? = null
) {
    onAny?.invoke(
        ResultHelper(
            isLoading = uiState.isLoading,
            isError = uiState.isError,
            isSuccess = uiState.isSuccess,
            result = uiState.data,
            message = uiState.message,
        )
    )
    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> onLoading?.invoke()

        is UiState.Success -> {
            onSuccess?.invoke(uiState.data)
            if (!uiState.hasBeenHandled) {
                onSuccessSingleTime?.invoke(uiState.data)
                uiState.hasBeenHandled = true
            }
        }

        is UiState.Error -> {
            onError?.invoke(uiState.message)
            if (!uiState.hasBeenHandled) {
                onErrorSingleTime?.invoke(uiState.message)
                uiState.hasBeenHandled = true
            }
        }

        is UiState.UnAuthorized -> {
            if (onUnAuthorized == null) {
//                    sharedPreferenceHelper.clean()
//                    encryptedSharedPreference.clean()
//                    startActivityAndClearStack(MainActivity::class.java)
                return
            }
            onUnAuthorized.invoke()
        }

        is UiState.NetworkError -> onNetworkError?.invoke()
        is UiState.UnKnownError -> onUnKnownError?.invoke()
    }
}