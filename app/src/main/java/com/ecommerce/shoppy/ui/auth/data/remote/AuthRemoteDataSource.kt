package com.ecommerce.shoppy.ui.auth.data.remote

import android.content.Context
import com.ecommerce.shoppy.base.BaseDataSource
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.remote.ApiService
import com.ecommerce.shoppy.ui.auth.data.remote.dto.LoginRequest

class AuthRemoteDataSource(context: Context, private val apis: ApiService) : BaseDataSource(context) {
    suspend fun login(request: LoginRequest): User {
        return fetch { apis.login(headers = headers, body = request.toMap()) }
    }
}