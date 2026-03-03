package com.example.myapplication.ui.order.presentation.listing

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.model.Order
import com.example.myapplication.core.model.OrderStatus
import com.example.myapplication.ui.order.domain.usecase.GetUserOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(private val getUserOrdersUseCase: GetUserOrdersUseCase) :
    BaseViewModel<OrdersUiState, Unit>(OrdersUiState()) {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val filteredOrders = MutableStateFlow<List<Order>>(emptyList())
    lateinit var orderStatus: List<Pair<OrderStatus, Int>>

    init {
        getUserOrders()
    }

    fun getUserOrders(fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = !fromSwipe, isRefreshing = fromSwipe))
        getUserOrdersUseCase.invoke(Unit).collect { result ->
            val selected = uiState.value.selectedOrderStatus
            _orders.value = result ?: emptyList()
            if (!::orderStatus.isInitialized) orderStatus = generateOrderStatusList(_orders.value)
            filteredOrders.value = if (selected == OrderStatus.All) _orders.value
            else _orders.value.filter { it.orderStatus == selected }
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
        }
    }

    fun onOrderStatusChanged(status: OrderStatus) {
        filteredOrders.value = if (status == OrderStatus.All) _orders.value
        else _orders.value.filter { it.orderStatus == status }
        updateUiState(uiState.value.copy(selectedOrderStatus = status))
    }

    private fun generateOrderStatusList(result: List<Order>) = listOf(
        OrderStatus.All to result.size,
        OrderStatus.Pending to result.count { it.orderStatus == OrderStatus.Pending },
        OrderStatus.Confirmed to result.count { it.orderStatus == OrderStatus.Confirmed },
        OrderStatus.Shipped to result.count { it.orderStatus == OrderStatus.Shipped },
        OrderStatus.Delivered to result.count { it.orderStatus == OrderStatus.Delivered },
        OrderStatus.Cancelled to result.count { it.orderStatus == OrderStatus.Cancelled },
        OrderStatus.Failed to result.count { it.orderStatus == OrderStatus.Failed },
    )
}