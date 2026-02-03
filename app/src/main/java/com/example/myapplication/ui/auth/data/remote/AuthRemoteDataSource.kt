package com.example.myapplication.ui.auth.data.remote

import android.content.Context
import com.example.myapplication.base.BaseDataSource
import com.example.myapplication.core.model.User
import com.example.myapplication.core.remote.ApiService
import com.example.myapplication.ui.auth.data.remote.dto.LoginRequest

class AuthRemoteDataSource(context: Context, private val apis: ApiService) : BaseDataSource(context) {
    suspend fun login(request: LoginRequest): User {
        return fetch { apis.login(headers = headers, body = request.toMap()) }
    }
}