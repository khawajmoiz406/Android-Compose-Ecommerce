package com.example.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.core.model.Order
import com.example.myapplication.core.model.OrderStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM `${DatabaseConfig.ORDER}` ORDER BY created_at DESC")
    fun getUserOrders(): Flow<List<Order>?>

    @Query("SELECT order_status FROM `${DatabaseConfig.ORDER}`")
    fun getUserOrdersCount(): Flow<List<OrderStatus>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM `${DatabaseConfig.ORDER}` WHERE id = :orderId")
    suspend fun getOrderDetail(orderId: Int): Order
}