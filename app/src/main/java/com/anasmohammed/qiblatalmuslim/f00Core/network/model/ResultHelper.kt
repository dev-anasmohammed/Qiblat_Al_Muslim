package com.anasmohammed.qiblatalmuslim.f00Core.network.model

data class ResultHelper<T>(
    val isLoading: Boolean,
    val isError: Boolean,
    val isSuccess: Boolean,
    val result: T?,
    val message: String?,
)