package com.ecommerce.shoppy.ui.cart.data.local.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ecommerce.shoppy.core.local.DatabaseConfig
import kotlinx.serialization.Serializable

@Serializable
@Stable
@Entity(tableName = DatabaseConfig.CART)
data class CartItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    val productId: Int,

    @ColumnInfo("quantity")
    val quantity: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
)