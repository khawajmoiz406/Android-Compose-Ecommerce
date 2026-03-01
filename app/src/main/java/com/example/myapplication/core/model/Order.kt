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

    @ColumnInfo("order_number")
    val orderNumber: String,

    @ColumnInfo("tracking_number")
    val trackingNumber: String,

    @ColumnInfo("items")
    val items: List<OrderItem>,

    @ColumnInfo("receipt")
    val receipt: Receipt,

    @ColumnInfo("shipping_method")
    val shippingMethod: Shipping,

    @ColumnInfo("shipping_address")
    val shippingAddress: Address,

    @ColumnInfo("payment_method")
    val paymentMethod: PaymentMethod,

    @ColumnInfo("order_status")
    val orderStatus: OrderStatus,

    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

sealed class PaymentMethod(val id: Int, val image: String, val name: String) {
    data object CashOnDelivery : PaymentMethod(1, "money", "Cash on Delivery")
    data object Card : PaymentMethod(2, "card", "Debit / Credit Card")
}

sealed class OrderStatus(val value: Int) {
    data object Pending : OrderStatus(1)
    data object Confirmed : OrderStatus(2)
    data object Shipped : OrderStatus(3)
    data object Delivered : OrderStatus(4)
    data object Cancelled : OrderStatus(5)
    data object Failed : OrderStatus(6)
}
