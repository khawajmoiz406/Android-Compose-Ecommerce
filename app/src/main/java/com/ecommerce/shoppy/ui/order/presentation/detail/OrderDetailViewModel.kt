package com.ecommerce.shoppy.ui.order.presentation.detail

import androidx.lifecycle.viewModelScope
import com.ecommerce.shoppy.base.BaseViewModel
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.ui.order.domain.usecase.GetOrderDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val getOrderDetailUseCase: GetOrderDetailUseCase) :
    BaseViewModel<OrderDetailUiState, Unit>(OrderDetailUiState()) {
    val order = MutableStateFlow<Order?>(null)

    fun getOrderDetail(orderId: Int, fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = !fromSwipe, isRefreshing = fromSwipe))
        val result = getOrderDetailUseCase.invoke(orderId)
        if (result.isSuccess) {
            val data = result.getOrNull()
            order.update { data }
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false, error = errorStr))
        }
    }
}