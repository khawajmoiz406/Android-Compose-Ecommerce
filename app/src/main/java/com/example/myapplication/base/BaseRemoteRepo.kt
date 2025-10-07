package com.example.myapplication.base

import android.content.Context
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.utils.NetworkUtils
import retrofit2.Response

open class BaseRemoteRepo(private val context: Context) {

    suspend fun <T> fetch(apiFunction: suspend () -> Response<T>): Result<T> {
        return try {
            if (!NetworkUtils.isNetworkAvailable(context)) Result.failure<T>(ApiException.NetworkException(context))

            val response = apiFunction.invoke()
            if (response.isSuccessful && response.body() != null) Result.success(response.body()!!)

            Result.failure(createHttpError(response))

        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun createHttpError(response: Response<*>): Throwable {
        return when (response.code()) {
            401 -> ApiException.AuthenticationException(context)
            403 -> ApiException.AuthorizationException(context)
            404 -> ApiException.NotFoundException(context)
            500 -> ApiException.ServerException(context)
            else -> ApiException.UnknownException(context)
        }
    }
}