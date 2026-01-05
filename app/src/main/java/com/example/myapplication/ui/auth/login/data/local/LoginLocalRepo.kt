package com.example.myapplication.ui.auth.login.data.local

import com.example.myapplication.models.response.User

interface LoginLocalRepo {
    suspend fun saveUserModel(user: User)
}