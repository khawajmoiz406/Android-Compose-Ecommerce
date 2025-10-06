package com.example.myapplication.base

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.utils.NetworkUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

open class BaseRemoteRepo : KoinComponent {
    private val context: Context by inject()

    suspend fun <T> fetch(apiFunction: suspend () -> Response<T>): Result<T> {
        return try {
            if (!NetworkUtils.isNetworkAvailable(context)) Result.failure<T>(Throwable(context.getString(R.string.internet_error)))

            val response = apiFunction.invoke()
            if (response.isSuccessful && response.body() != null) Result.success(response.body()!!)

            val error = response.errorBody()?.string() ?: response.message() ?: context.getString(R.string.unknown_error)
            Result.failure(Throwable(error))

        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: UnknownHostException) {
            Result.failure(e)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}