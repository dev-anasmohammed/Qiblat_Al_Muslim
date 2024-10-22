package com.anasmohammed.qiblatalmuslim.f00Core.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersSetupInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder().apply {
                    addHeader("Accept", "application/json")
                }
                .build())
    }
}