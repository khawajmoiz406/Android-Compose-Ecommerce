package com.example.myapplication.core.local.converter

import androidx.room.TypeConverter
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Shipping
import com.example.myapplication.core.shared.data.local.entity.OrderItem
import com.example.myapplication.core.shared.data.local.entity.Receipt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object OrderConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItemList(value: List<OrderItem>): String =
        gson.toJson(value)

    @TypeConverter
    fun toOrderItemList(value: String): List<OrderItem> =
        gson.fromJson(value, object : TypeToken<List<OrderItem>>() {}.type)

    @TypeConverter
    fun fromReceipt(value: Receipt): String =
        gson.toJson(value)

    @TypeConverter
    fun toReceipt(value: String): Receipt =
        gson.fromJson(value, Receipt::class.java)

    @TypeConverter
    fun fromShipping(value: Shipping): String =
        gson.toJson(value)

    @TypeConverter
    fun toShipping(value: String): Shipping =
        gson.fromJson(value, Shipping::class.java)

    @TypeConverter
    fun fromAddress(value: Address): String =
        gson.toJson(value)

    @TypeConverter
    fun toAddress(value: String): Address =
        gson.fromJson(value, Address::class.java)
}