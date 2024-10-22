package com.anasmohammed.qiblatalmuslim.f00Core.network.handlers

import android.util.Log
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.BaseApiResponse
import com.anasmohammed.qiblatalmuslim.f00Core.network.model.DomainResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException
import java.io.Reader

suspend fun <T, Y> globalNetworkCall(
    action: suspend () -> Response<T>,
    mapper: (T?) -> Y,
    doAfterSuccess: ((Y) -> Unit)? = null
): DomainResult<Y> {
    return try {
        val response = action.invoke()

        val body = response.body()

        /** successful **/
        if (response.isSuccessful && body == null) {
            return DomainResult.Error(message = "Null body")
        }

        if (response.isSuccessful && body != null) {
            Log.e("globalNetworkCall:", "body $body")
            doAfterSuccess?.invoke(mapper(body))
            return DomainResult.Success(data = mapper(body))
        }

        /** not successful **/
        val errorBody = getBodyError(response)
        val errorMsg = errorBody?.message

        if (!response.isSuccessful && response.code() == 401) {
            return DomainResult.UnAuthorized()
        }

        if (!response.isSuccessful && response.code() == 405) {
            Log.e("globalNetworkCall:", "check POST / GET")
            return DomainResult.UnKnownError()
        }

        if (!response.isSuccessful && response.code() / 100 == 5) {
            return DomainResult.Error(message = "Server error")
        }

        if (!response.isSuccessful) {
            return DomainResult.Error(message = errorMsg)
        }

        return DomainResult.Nothing()
    } catch (e: Exception) {

        Log.e("globalNetworkCall:", "message: ${e.message}\n.")
        Log.e("globalNetworkCall:", "cause: ${e.cause}\n.")
        Log.e("globalNetworkCall:", "localizedMessage: ${e.localizedMessage}\n.")

        when (e) {
            is IOException -> {
                val exceptionMessage = e.localizedMessage ?: ""
                return if (exceptionMessage.contains("Unable to resolve host")
                    || exceptionMessage.contains("timeout")
                    || exceptionMessage.contains("Connection reset")
                    || exceptionMessage.contains("java.net.SocketException: Socket closed")
                    || exceptionMessage.contains("Failed to connect to")
                    || e.cause.toString().contains("Network is unreachable")
                ) {
                    DomainResult.NetworkError()
                } else {
                    DomainResult.UnKnownError()
                }
            }

            else -> {
                DomainResult.Error(message = "error")
            }
        }
    }
}

fun <T> getBodyError(response: Response<T>, gson: Gson = Gson()): BaseApiResponse<T>? {
    return try {
        val errorBody: Reader? = response.errorBody()?.charStream()
        if (errorBody != null) {
            val type = object : TypeToken<BaseApiResponse<T>>() {}.type
            gson.fromJson<BaseApiResponse<T>>(errorBody, type)
        } else {
            null
        }
    } catch (exception: Exception) {
        null
    }
}



