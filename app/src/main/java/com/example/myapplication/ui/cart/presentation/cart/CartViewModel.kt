package com.example.myapplication.ui.cart.presentation.cart

import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.components.state.FieldState
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.core.shared.domain.usecase.RemoveFromCartUseCase
import com.example.myapplication.core.shared.domain.usecase.ToggleFavouriteUseCase
import com.example.myapplication.ui.cart.data.remote.dto.UpdateCartItemRequest
import com.example.myapplication.ui.cart.domain.usecase.GetCartUseCase
import com.example.myapplication.ui.cart.domain.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateQuantityUseCase: UpdateQuantityUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) : BaseViewModel<CartUiState, Unit>(CartUiState()) {
    val cart: MutableStateFlow<Cart?> = MutableStateFlow(null)
    val promo: MutableStateFlow<PromoCode?> = MutableStateFlow(null)

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

    fun toggleFavourite(productId: Int) = viewModelScope.launch {
        cart.value?.let {
            val result = toggleFavouriteUseCase.invoke(productId)
            if (result.isSuccess) {
                this@CartViewModel.cart.update {
                    it?.copy(
                        items = it.items.map { item ->
                            if (item.cartItem.productId == productId)
                                item.copy(product = item.product.copy(isFavourite = !item.product.isFavourite))
                            else item
                        }
                    )
                }
            }
        }
    }

    fun onPromoFieldChanged(value: String) {
        updateUiState(uiState.value.copy(promoFieldState = FieldState(value)))
    }

    fun checkPromoCode() = viewModelScope.launch {
        val promoField = uiState.value.promoFieldState
        if (promoField.value.isEmpty())
            return@launch updateUiState(uiState.value.copy(promoFieldState = promoField.copy(error = R.string.field_is_required)))

        updateUiState(uiState.value.copy(promoLoading = true))
        delay(2000L)
        promo.value = PromoCode(id = 101, uiState.value.promoFieldState.value, 20.0)
        updateUiState(uiState.value.copy(promoLoading = false))
    }

    fun removePromo() = viewModelScope.launch {
        updateUiState(uiState.value.copy(promoLoading = true))
        promo.value = null
        updateUiState(uiState.value.copy(promoLoading = false))
    }
}