package com.example.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.core.model.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM `${DatabaseConfig.ORDER}` WHERE order_status = :status")
    fun getUserOrders(status: Int): Flow<List<Order>?>

    @Query("SELECT COUNT(*) FROM `${DatabaseConfig.ORDER}` WHERE order_status = :status")
    fun getUserOrdersCount(status: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long
}