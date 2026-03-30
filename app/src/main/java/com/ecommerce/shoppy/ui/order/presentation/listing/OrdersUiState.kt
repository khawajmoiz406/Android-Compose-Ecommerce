package com.ecommerce.shoppy.ui.order.presentation.listing

import androidx.compose.runtime.Stable
import com.ecommerce.shoppy.core.model.OrderStatus

@Stable
data class OrdersUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val selectedOrderStatus: OrderStatus = OrderStatus.All
)