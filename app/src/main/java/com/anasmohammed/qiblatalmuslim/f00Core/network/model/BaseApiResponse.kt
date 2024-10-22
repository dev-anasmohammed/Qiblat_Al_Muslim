package com.anasmohammed.qiblatalmuslim.f00Core.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseApiResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("message")
    val message: String = "",
)