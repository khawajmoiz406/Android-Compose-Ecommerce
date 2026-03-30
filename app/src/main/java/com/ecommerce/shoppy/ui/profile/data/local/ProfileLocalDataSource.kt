package com.ecommerce.shoppy.ui.profile.data.local

import com.ecommerce.shoppy.config.theme.ThemeMode
import com.ecommerce.shoppy.core.local.AppDatabase
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.pref.EncryptedSharedPref
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProfileLocalDataSource(
    private val database: AppDatabase,
    private val pref: EncryptedSharedPref
) {
    fun getUserInfo(): User? {
        return pref.getModel(object : TypeToken<User>() {})
    }

    fun getTotalOrders(): Flow<List<OrderStatus>?> {
        return database.getOrderDao().getUserOrdersCount()
    }

    fun changeThemeMode(themeMode: ThemeMode): User? {
        var user = pref.getModel(object : TypeToken<User>() {})
        user = user?.copy(themeMode = themeMode.value)
        pref.putModel(user, object : TypeToken<User>() {})
        return user
    }

    fun changeNotification(notificationEnabled: Boolean): User? {
        var user = pref.getModel(object : TypeToken<User>() {})
        user = user?.copy(notificationEnabled = notificationEnabled)
        pref.putModel(user, object : TypeToken<User>() {})
        return user
    }

    suspend fun logout() {
        pref.removeModel(object : TypeToken<User>() {})
        pref.clearAll()
        withContext(Dispatchers.IO) { database.clearAllTables() }
    }
}