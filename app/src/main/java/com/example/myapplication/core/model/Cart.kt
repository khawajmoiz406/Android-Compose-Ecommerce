package com.example.myapplication.core.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.config.utils.Constants.DELIVERY_FEES
import com.example.myapplication.config.utils.Constants.PLATFORM_FEES
import com.example.myapplication.config.utils.Constants.VAT
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.ui.cart.data.local.relation.CartItemWithProduct

data class Cart(val items: List<CartItemWithProduct>) {
    fun getSubTotalPrice(): Double = items.sumOf { item ->
        item.cartItem.quantity * (item.product.price ?: 0.0)
    }

    fun getSubTotalWithoutDiscount(): Double = items.sumOf { item ->
        item.cartItem.quantity * ((item.product.price ?: 0.0) + item.product.getDiscountPrice())
    }

    fun getSubTotalDiscount(): Double = items.sumOf { item ->
        item.cartItem.quantity * item.product.getDiscountPrice()
    }

    fun getTotal(): Double {
        val subTotal = getSubTotalPrice()
        return subTotal + PLATFORM_FEES + DELIVERY_FEES + VAT
    }
}
