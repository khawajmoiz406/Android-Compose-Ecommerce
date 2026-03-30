package com.ecommerce.shoppy.ui.profile.domain.repository

import com.ecommerce.shoppy.config.theme.ThemeMode
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.User
import kotlinx.coroutines.flow.Flow


interface ProfileRepository {
    fun getTotalOrders(): Flow<List<OrderStatus>?>
    suspend fun getUserInfo(): Result<User?>
    suspend fun changeThemeMode(themeMode: ThemeMode): Result<User?>
    suspend fun changeNotificationSettings(notificationEnable: Boolean): Result<User?>
    suspend fun logout(): Result<Unit?>
}