package com.example.myapplication.core.model

import com.example.myapplication.config.utils.Constants.DELIVERY_FEES
import com.example.myapplication.config.utils.Constants.PLATFORM_FEES
import com.example.myapplication.config.utils.Constants.VAT
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

    fun getTotal(promo: Double? = null): Double {
        val subTotal = getSubTotalPrice()
        var total = (subTotal + PLATFORM_FEES + DELIVERY_FEES + VAT)
        promo?.let { total -= it }
        return total
    }
}
