package com.example.myapplication.ui.address.data.repository

import com.example.myapplication.core.model.Address
import com.example.myapplication.ui.address.data.local.AddressLocalDataSource
import com.example.myapplication.ui.address.data.remote.AddressRemoteDataSource
import com.example.myapplication.ui.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class AddressRepositoryImpl(
    private val localDataSource: AddressLocalDataSource,
    private val remoteDataSource: AddressRemoteDataSource
) : AddressRepository {
    override fun getUserAddresses(): Flow<List<Address>?> {
        return localDataSource.getUserAddresses()
    }

    override suspend fun getAddress(id: Int): Result<Address?> = try {
        Result.success(localDataSource.getAddress(id))
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun addNewAddress(address: Address): Result<Unit?> = try {
        Result.success(localDataSource.addNewAddress(address))
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun updateAddress(address: Address): Result<Unit?> = try {
        Result.success(localDataSource.updateAddress(address))
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun deleteAddress(id: Int): Result<Unit?> = try {
        Result.success(localDataSource.deleteAddress(id))
    } catch (ex: Exception) {
        Result.failure(ex)
    }
}