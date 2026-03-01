package com.example.myapplication.ui.address.data.local

import com.example.myapplication.core.local.AppDatabase
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.pref.EncryptedSharedPref
import kotlinx.coroutines.flow.Flow

class AddressLocalDataSource(
    private val database: AppDatabase,
    private val pref: EncryptedSharedPref
) {
    fun getUserAddresses(): Flow<List<Address>?> {
        return database.getAddressDao().getUserAddresses()
    }

    suspend fun getAddress(id: Int): Address? {
        return database.getAddressDao().getAddress(id)
    }

    suspend fun addNewAddress(address: Address) {
        if(address.defaultAddress) database.getAddressDao().clearAllDefaults()
        return database.getAddressDao().insertAddress(address)
    }

    suspend fun updateAddress(address: Address) {
        if(address.defaultAddress) database.getAddressDao().clearAllDefaults()
        return database.getAddressDao().updateAddress(address)
    }

    suspend fun deleteAddress(id: Int) {
        return database.getAddressDao().removeAddress(id)
    }
}