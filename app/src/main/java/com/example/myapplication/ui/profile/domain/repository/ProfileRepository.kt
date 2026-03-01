package com.example.myapplication.ui.profile.domain.repository

import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.cart.data.remote.dto.OrderStatus
import kotlinx.coroutines.flow.Flow


interface ProfileRepository {
    fun getTotalOrders(status: OrderStatus): Flow<Int?>
    suspend fun getUserInfo(): Result<User?>
    suspend fun changeThemeMode(themeMode: ThemeMode): Result<User?>
    suspend fun changeNotificationSettings(notificationEnable: Boolean): Result<User?>
    suspend fun logout(): Result<Unit?>
}