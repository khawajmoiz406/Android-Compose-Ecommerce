package com.example.myapplication.ui.cart.data.remote.dto

import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PaymentMethod
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.core.model.Shipping

data class CheckoutRequest(
    val cart: Cart,
    val promoCode: PromoCode?,
    val shippingMethod: Shipping,
    val paymentMethod: PaymentMethod,
    val shippingAddress: Address?,
    val phoneNumber: String
)