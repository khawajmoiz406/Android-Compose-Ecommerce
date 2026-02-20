package com.example.myapplication.core.model

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.core.shared.data.local.entity.OrderItem
import com.example.myapplication.core.shared.data.local.entity.Receipt

@Stable
@Entity(tableName = DatabaseConfig.ORDER)
data class Order(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("items")
    val items: List<OrderItem>,

    @ColumnInfo("receipt")
    val receipt: Receipt,

    @ColumnInfo("shipping_method")
    val shippingMethod: Shipping,

    @ColumnInfo("shipping_address")
    val shippingAddress: Address,

    @ColumnInfo("payment_method")
    val paymentMethod: Int,

    @ColumnInfo("order_status")
    val orderStatus: Int,

    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)