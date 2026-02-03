package com.example.myapplication.ui.product_detail.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.shared.data.remote.dto.AddProductToCartRequest
import com.example.myapplication.core.shared.domain.usecase.AddToCartUseCase
import com.example.myapplication.core.shared.domain.usecase.RemoveFromCartUseCase
import com.example.myapplication.core.shared.domain.usecase.ToggleFavouriteUseCase
import com.example.myapplication.ui.product_detail.domain.usecase.GetProductDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val detailUseCase: GetProductDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) :
    BaseViewModel<ProductDetailUiState, Unit>(ProductDetailUiState()) {
    val product: MutableStateFlow<Product?> = MutableStateFlow(null)

    fun getProductDetail(id: String) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        detailUseCase.invoke(id).collect { result ->
            if (result.isSuccess) {
                val data = result.getOrNull()
                product.update { data }
                updateUiState(uiState.value.copy(isLoading = false))
            } else {
                val errorStr = result.exceptionOrNull().toErrorString()
                updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
            }
        }
    }

    fun addProductToCart(product: Product, quantity: Int) = viewModelScope.launch {
        val requestParams = AddProductToCartRequest(product, quantity)
        updateUiState(uiState.value.copy(isLoading = true))
        val result = addToCartUseCase.invoke(requestParams)
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(isLoading = false))
            this@ProductDetailViewModel.product.update { it?.copy(addedToCart = true) }
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }

    }

    fun removeProductFromCart(productId: Int) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = removeFromCartUseCase.invoke(productId)
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(isLoading = false))
            this@ProductDetailViewModel.product.update { it?.copy(addedToCart = false) }
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }

    }

    fun toggleFav() {
        product.update { it?.copy(isFavourite = !it.isFavourite) }
    }
}