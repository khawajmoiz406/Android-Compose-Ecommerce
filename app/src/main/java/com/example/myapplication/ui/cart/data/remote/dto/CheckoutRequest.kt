package com.example.myapplication.ui.cart.data.remote.dto

import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.core.model.Shipping

data class CheckoutRequest(
    val cart: Cart,
    val promoCode: PromoCode?,
    val shippingMethod: Shipping,
    val paymentMethod: PaymentMethod,
    val shippingAddress: Address?,
)

sealed class PaymentMethod(val id: Int, val image: String, val name: String) {
    data object CashOnDelivery : PaymentMethod(1, "money", "Cash on Delivery")
    data object Card : PaymentMethod(2, "card", "Debit / Credit Card")
}

sealed class OrderStatus(val value: Int) {
    data object InProgress : OrderStatus(1)
    data object Dispatched : OrderStatus(2)
    data object Completed : OrderStatus(3)
    data object Cancelled : OrderStatus(4)
}
