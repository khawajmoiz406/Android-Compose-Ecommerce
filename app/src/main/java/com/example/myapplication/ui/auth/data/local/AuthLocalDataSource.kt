package com.example.myapplication.ui.auth.data.local

import com.example.myapplication.core.model.User
import com.example.myapplication.core.pref.EncryptedSharedPref
import com.google.gson.reflect.TypeToken

class AuthLocalDataSource(private val pref: EncryptedSharedPref) {
    fun saveUserModel(user: User) {
        pref.putModel(user, object : TypeToken<User>() {})
    }
}