package com.ecommerce.shoppy.core.pref

import android.content.Context
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.pref.SharedPrefKeys.NOTIFICATION_PERMISSION_ASKED
import com.google.gson.reflect.TypeToken

object SharedPrefUtils {
    fun getCurrentUser(context: Context): User? {
        return EncryptedSharedPref.getInstance(context).getModel(object : TypeToken<User>() {})
    }

    fun isNotificationPermissionAsked(context: Context): Boolean {
        return EncryptedSharedPref.getInstance(context).getBool(NOTIFICATION_PERMISSION_ASKED)
    }
}