package com.ecommerce.shoppy.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ecommerce.shoppy.core.local.DatabaseConfig
import com.ecommerce.shoppy.core.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Update
    suspend fun updateAddress(address: Address)

    @Query("DELETE FROM ${DatabaseConfig.ADDRESS} WHERE id = :id")
    suspend fun removeAddress(id: Int)

    @Query("SELECT * FROM ${DatabaseConfig.ADDRESS}")
    fun getUserAddresses(): Flow<List<Address>?>

    @Query("SELECT * FROM ${DatabaseConfig.ADDRESS} WHERE default_address = 1 ORDER BY id ASC LIMIT 1")
    suspend fun getDefaultAddress(): Address?

    @Query("SELECT * FROM ${DatabaseConfig.ADDRESS} WHERE id = :id")
    suspend fun getAddress(id: Int): Address?

    @Query("UPDATE ${DatabaseConfig.ADDRESS} SET default_address = 0")
    suspend fun clearAllDefaults()
}