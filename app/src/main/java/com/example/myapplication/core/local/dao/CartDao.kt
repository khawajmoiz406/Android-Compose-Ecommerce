package com.example.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import com.example.myapplication.ui.cart.data.local.relation.CartItemWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT COUNT(*) FROM ${DatabaseConfig.CART}")
    fun getCartCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Transaction
    @Query("SELECT * FROM ${DatabaseConfig.CART}")
    suspend fun getCartItems(): List<CartItemWithProduct>

    @Query("DELETE FROM ${DatabaseConfig.CART} WHERE product_id = :productId")
    suspend fun removeCartItem(productId: Int)

    @Query("UPDATE ${DatabaseConfig.CART} SET quantity = :quantity, updated_at = :updatedAt WHERE product_id = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int, updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM ${DatabaseConfig.CART}")
    suspend fun clearAll()
}