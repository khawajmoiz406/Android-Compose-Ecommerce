package com.ecommerce.shoppy.ui.address.domain.repository

import com.ecommerce.shoppy.core.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getUserAddresses(): Flow<List<Address>?>
    suspend fun getAddress(id: Int): Result<Address?>
    suspend fun addNewAddress(address: Address): Result<Unit?>
    suspend fun updateAddress(address: Address): Result<Unit?>
    suspend fun deleteAddress(id: Int): Result<Unit?>
}