package com.example.myapplication.base

import android.content.Context
import com.example.myapplication.config.utils.NetworkUtils
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.helper.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

open class BaseRemoteRepo(private val context: Context) {
    val headers = hashMapOf("Content-Type" to "application/json")

    suspend fun <T> fetch(addToken: Boolean = true, apiFunction: suspend () -> Response<T>): T {
        try {
            if (!NetworkUtils.isNetworkAvailable(context))
                throw ApiException.NetworkException(context)

            val response = apiFunction.invoke()

            if (response.isSuccessful && response.body() != null)
                return response.body()!!

            throw createHttpError(response)

        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun createHttpError(response: Response<*>): Throwable {
        return when (response.code()) {
            401 -> ApiException.AuthenticationException(context)
            403 -> ApiException.AuthorizationException(context)
            500 -> ApiException.ServerException(context)
            404 -> ApiException.NotFoundException(context)
            else -> {
                val jsonBody = response.errorBody()?.string()
                val errorJson = Gson().fromJson(jsonBody, ErrorResponse::class.java)
                ApiException.UnknownException(context, exception = errorJson.message)
            }
        }
    }
}