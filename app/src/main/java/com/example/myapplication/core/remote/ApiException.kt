package com.example.myapplication.core.remote

import android.content.Context
import com.example.myapplication.R

sealed class ApiException(val error: String) : Throwable() {
    data class NetworkException(private val context: Context) :
        ApiException(error = context.getString(R.string.internet_error))

    data class AuthenticationException(private val context: Context) :
        ApiException(error = context.getString(R.string.authentication_error))

    data class AuthorizationException(private val context: Context) :
        ApiException(error = context.getString(R.string.authorization_error))

    data class NotFoundException(private val context: Context) :
        ApiException(error = context.getString(R.string.not_found_error))

    data class ServerException(private val context: Context) :
        ApiException(error = context.getString(R.string.internal_server_error))

    data class UnknownException(private val context: Context) :
        ApiException(error = context.getString(R.string.unknown_error))
}
