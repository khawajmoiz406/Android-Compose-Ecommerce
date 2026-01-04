package com.example.myapplication.ui.auth.login.data

import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.models.response.User
import com.example.myapplication.ui.auth.login.data.local.LoginLocalRepo
import com.example.myapplication.ui.auth.login.data.remote.LoginRemoteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val remoteRepo: LoginRemoteRepo, private val localRepo: LoginLocalRepo) :
    LoginRepository {
    override suspend fun login(request: LoginRequest): Flow<Result<User>> = flow {
        try {
            val user = remoteRepo.login(request)
            localRepo.saveUserModel(user)

            emit(Result.success(user))
        } catch (ex: ApiException) {
            emit(Result.failure(ex))
        }
    }
}