package com.ecommerce.shoppy.ui.cart.presentation.checkout

sealed class CheckoutEvents {
    class OnOrderPlaced(val orderId: Int) : CheckoutEvents()
    class OnError(val error: String) : CheckoutEvents()
}