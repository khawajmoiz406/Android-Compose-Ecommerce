package com.ecommerce.shoppy.ui.cart.data.remote.dto

import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.Cart
import com.ecommerce.shoppy.core.model.PaymentMethod
import com.ecommerce.shoppy.core.model.PromoCode
import com.ecommerce.shoppy.core.model.Shipping

data class CheckoutRequest(
    val cart: Cart,
    val promoCode: PromoCode?,
    val shippingMethod: Shipping,
    val paymentMethod: PaymentMethod,
    val shippingAddress: Address?,
    val phoneNumber: String
)