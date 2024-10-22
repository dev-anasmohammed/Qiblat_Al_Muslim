package com.anasmohammed.qiblatalmuslim.f00Core.network.model

sealed class UiState<T>(
    var data: T? = null,
    var message: String? = null,
    var hasBeenHandled: Boolean = false
) {
    class Idle<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    class NetworkError<T> : UiState<T>()
    class UnAuthorized<T> : UiState<T>()
    class UnKnownError<T> : UiState<T>()

    class Success<T>(
        data:T,
        message: String? = "",
        hasBeenHandled: Boolean = false
    ) : UiState<T>(data, message, hasBeenHandled)

    class Error<T>(
        message: String?,
        data: T? = null,
        hasBeenHandled: Boolean = false
    ) : UiState<T>(data, message, hasBeenHandled)

}

val UiState<*>.isLoading get() = this is UiState.Loading
val UiState<*>.isSuccess get() = this is UiState.Success
val UiState<*>.isError
    get() = this is UiState.Error<*> ||
            this is UiState.NetworkError<*> ||
            this is UiState.UnAuthorized<*> ||
            this is UiState.UnKnownError<*>