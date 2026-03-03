package com.example.myapplication.ui.order.presentation.listing

import androidx.compose.runtime.Stable
import com.example.myapplication.core.model.OrderStatus

@Stable
data class OrdersUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val selectedOrderStatus: OrderStatus = OrderStatus.All
)