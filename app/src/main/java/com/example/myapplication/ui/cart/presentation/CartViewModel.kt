package com.example.myapplication.ui.cart.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.shared.domain.usecase.RemoveFromCartUseCase
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest
import com.example.myapplication.ui.cart.domain.usecase.GetCartUseCase
import com.example.myapplication.ui.cart.domain.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateQuantityUseCase: UpdateQuantityUseCase
) : BaseViewModel<CartUiState, Unit>(CartUiState()) {
    val cart: MutableStateFlow<Cart?> = MutableStateFlow(null)

    init {
        getUserCart()
    }

    fun getUserCart() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = getCartUseCase.invoke(Unit)
        if (result.isSuccess) {
            val data = result.getOrNull()
            cart.update { data }
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }

    }

    fun removeCartItem(productId: Int) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = removeFromCartUseCase.invoke(productId)
        if (result.isSuccess) {
            cart.update { it?.copy(items = it.items.filterNot { product -> product.cartItem.productId == productId }) }
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }

    }

    fun updateQuantity(productId: Int, quantity: Int) = viewModelScope.launch {
        val requestParams = UpdateCartItemRequest(productId, quantity)
        updateUiState(uiState.value.copy(isLoading = true))

        val result = updateQuantityUseCase.invoke(requestParams)
        if (result.isSuccess) {
            cart.update {
                it?.copy(
                    items = it.items.map { item ->
                        if (item.cartItem.productId == productId)
                            item.copy(cartItem = item.cartItem.copy(quantity = quantity))
                        else item
                    }
                )
            }
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }
    }

}