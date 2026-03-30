package com.ecommerce.shoppy.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ecommerce.shoppy.config.theme.Blue
import com.ecommerce.shoppy.config.theme.Brown
import com.ecommerce.shoppy.config.theme.Green
import com.ecommerce.shoppy.config.theme.Orange
import com.ecommerce.shoppy.config.theme.Pink40
import com.ecommerce.shoppy.config.theme.Purple40
import com.ecommerce.shoppy.core.local.DatabaseConfig
import com.ecommerce.shoppy.core.shared.data.local.entity.OrderItem
import com.ecommerce.shoppy.core.shared.data.local.entity.Receipt
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
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

    @ColumnInfo("phone_number")
    val phoneNumber: String,

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
) {
    fun getProductsNames(): String = when {
        items.size == 1 -> items.first().productName
        else -> "${items.first().productName} & ${items.size - 1} more"
    }

    fun getOrderProgress(): Float = when (orderStatus) {
        OrderStatus.Pending -> 0f
        OrderStatus.Confirmed -> 0.50f
        OrderStatus.Shipped -> 0.75f
        OrderStatus.Delivered -> 1.0f
        else -> 0.0f
    }
}

@Serializable
sealed class PaymentMethod(val id: Int, val image: String, val name: String) {
    data object CashOnDelivery : PaymentMethod(1, "money", "Cash on Delivery")
    data object Card : PaymentMethod(2, "card", "Debit / Credit Card")
}

@Serializable
sealed class OrderStatus(val value: Int, val icon: String, @Contextual val color: Color) {
    data object All : OrderStatus(0, "", Pink40)
    data object Pending : OrderStatus(1, "clock", Brown)
    data object Confirmed : OrderStatus(2, "lock", Purple40)
    data object Shipped : OrderStatus(3, "truck", Blue)
    data object Delivered : OrderStatus(4, "check_circle", Green)
    data object Cancelled : OrderStatus(5, "cancel_circle", Orange)
    data object Failed : OrderStatus(6, "cancel_circle", Orange)
}
