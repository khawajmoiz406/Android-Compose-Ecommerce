package com.example.myapplication.ui.profile.data.local

import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.User
import com.example.myapplication.core.pref.EncryptedSharedPref
import com.example.myapplication.ui.cart.data.remote.dto.OrderStatus
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

    fun getTotalOrders(status: OrderStatus): Flow<Int> {
        return database.getOrderDao().getUserOrdersCount(status.value)
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
        withContext(Dispatchers.IO) { database.clearAllTables() }
    }
}