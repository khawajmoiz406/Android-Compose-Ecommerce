package com.ecommerce.shoppy.ui.profile.data.repository

import com.ecommerce.shoppy.config.theme.ThemeMode
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.ui.profile.data.local.ProfileLocalDataSource
import com.ecommerce.shoppy.ui.profile.data.remote.ProfileRemoteDataSource
import com.ecommerce.shoppy.ui.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val localDataSource: ProfileLocalDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {
    override suspend fun getUserInfo(): Result<User?> = try {
        val user = localDataSource.getUserInfo()
        Result.success(user)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun changeThemeMode(themeMode: ThemeMode): Result<User?> = try {
        val user = localDataSource.changeThemeMode(themeMode)
        Result.success(user)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun changeNotificationSettings(notificationEnable: Boolean): Result<User?> =
        try {
            val user = localDataSource.changeNotification(notificationEnable)
            Result.success(user)
        } catch (ex: Exception) {
            Result.failure(ex)
        }

    override suspend fun logout(): Result<Unit?> = try {
        localDataSource.logout()
        Result.success(Unit)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override fun getTotalOrders(): Flow<List<OrderStatus>?> {
        return localDataSource.getTotalOrders()
    }
}