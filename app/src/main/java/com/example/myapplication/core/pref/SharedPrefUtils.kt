package com.example.myapplication.core.pref

import android.content.Context
import com.example.myapplication.core.model.User
import com.example.myapplication.core.pref.SharedPrefKeys.NOTIFICATION_PERMISSION_ASKED
import com.google.gson.reflect.TypeToken

object SharedPrefUtils {
    fun getCurrentUser(context: Context): User? {
        return EncryptedSharedPref.getInstance(context).getModel(object : TypeToken<User>() {})
    }

    fun isNotificationPermissionAsked(context: Context): Boolean {
        return EncryptedSharedPref.getInstance(context).getBool(NOTIFICATION_PERMISSION_ASKED)
    }
}