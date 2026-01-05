package com.example.myapplication.ui.auth.login.data.local

import com.example.myapplication.core.local.pref.EncryptedSharedPref
import com.example.myapplication.models.response.User
import com.google.gson.reflect.TypeToken

class LoginLocalRepoImpl(private val pref: EncryptedSharedPref) : LoginLocalRepo {
    override suspend fun saveUserModel(user: User) {
        pref.putModel(user, object : TypeToken<User>() {})
    }
}