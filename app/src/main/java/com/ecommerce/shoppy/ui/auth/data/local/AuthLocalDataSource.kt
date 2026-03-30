package com.ecommerce.shoppy.ui.auth.data.local

import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.pref.EncryptedSharedPref
import com.google.gson.reflect.TypeToken

class AuthLocalDataSource(private val pref: EncryptedSharedPref) {
    fun saveUserModel(user: User) {
        pref.putModel(user, object : TypeToken<User>() {})
    }
}