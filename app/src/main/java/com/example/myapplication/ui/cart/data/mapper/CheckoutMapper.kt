package com.example.myapplication.ui.cart.data.mapper

import com.example.myapplication.config.utils.Constants.PLATFORM_FEES
import com.example.myapplication.config.utils.Constants.VAT
import com.example.myapplication.core.model.Order
import com.example.myapplication.core.shared.data.local.entity.OrderItem
import com.example.myapplication.core.shared.data.local.entity.Receipt
import com.example.myapplication.ui.cart.data.local.relation.CartItemWithProduct
import com.example.myapplication.ui.cart.data.remote.dto.CheckoutRequest
import com.example.myapplication.ui.cart.data.remote.dto.OrderStatus

fun CartItemWithProduct.toOrderItem(): OrderItem = OrderItem(
    productId = product.id ?: -1,
    productName = product.title ?: "",
    quantity = cartItem.quantity,
    discount = product.getDiscountPrice() * cartItem.quantity,
    originalPrice = product.getPriceBeforeDiscount(),
    discountedPrice = product.price ?: 0.0,
    subtotal = (product.price ?: 0.0) * cartItem.quantity,
)

fun CheckoutRequest.toOrder(): Order = Order(
    items = cart.items.map { it.toOrderItem() },
    shippingMethod = shippingMethod,
    shippingAddress = shippingAddress!!,
    paymentMethod = paymentMethod.id,
    orderStatus = OrderStatus.InProgress.value,
    receipt = Receipt(
        subtotal = cart.getSubTotalPrice(),
        productDiscount = cart.getSubTotalDiscount(),
        promoDiscount = promoCode?.discountPrice,
        vat = VAT,
        platformFees = PLATFORM_FEES,
        shippingFee = shippingMethod.price,
        totalAmount = cart.getTotal(promoCode?.discountPrice),
    ),
)