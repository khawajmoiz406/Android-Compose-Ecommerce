package com.ecommerce.shoppy.core.local.converter

import androidx.room.TypeConverter
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.PaymentMethod
import com.ecommerce.shoppy.core.model.Shipping
import com.ecommerce.shoppy.core.shared.data.local.entity.OrderItem
import com.ecommerce.shoppy.core.shared.data.local.entity.Receipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object OrderConverters {
    @TypeConverter
    fun fromOrderItemList(value: List<OrderItem>): String = Json.encodeToString(value)

    @TypeConverter
    fun toOrderItemList(value: String): List<OrderItem> = Json.decodeFromString(value)

    @TypeConverter
    fun fromReceipt(value: Receipt): String = Json.encodeToString(value)

    @TypeConverter
    fun toReceipt(value: String): Receipt = Json.decodeFromString(value)

    @TypeConverter
    fun fromShipping(value: Shipping): String = Json.encodeToString(value)

    @TypeConverter
    fun toShipping(value: String): Shipping = Json.decodeFromString(value)

    @TypeConverter
    fun fromAddress(value: Address): String = Json.encodeToString(value)

    @TypeConverter
    fun toAddress(value: String): Address = Json.decodeFromString(value)

    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): Int = value.value

    @TypeConverter
    fun toOrderStatus(value: Int): OrderStatus = when (value) {
        1 -> OrderStatus.Pending
        2 -> OrderStatus.Confirmed
        3 -> OrderStatus.Shipped
        4 -> OrderStatus.Delivered
        5 -> OrderStatus.Cancelled
        6 -> OrderStatus.Failed
        else -> throw IllegalArgumentException("Unknown OrderStatus: $value")
    }

    @TypeConverter
    fun fromPaymentMethod(value: PaymentMethod): Int = value.id

    @TypeConverter
    fun toPaymentMethod(value: Int): PaymentMethod = when (value) {
        1 -> PaymentMethod.CashOnDelivery
        2 -> PaymentMethod.Card
        else -> throw IllegalArgumentException("Unknown PaymentMethod: $value")
    }
}