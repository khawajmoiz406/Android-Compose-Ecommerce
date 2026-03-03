package com.example.myapplication.ui.profile.domain.repository

import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.core.model.OrderStatus
import com.example.myapplication.core.model.User
import kotlinx.coroutines.flow.Flow


interface ProfileRepository {
    fun getTotalOrders(): Flow<List<OrderStatus>?>
    suspend fun getUserInfo(): Result<User?>
    suspend fun changeThemeMode(themeMode: ThemeMode): Result<User?>
    suspend fun changeNotificationSettings(notificationEnable: Boolean): Result<User?>
    suspend fun logout(): Result<Unit?>
}