package com.example.myapplication.ui.product_detail.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.product_detail.domain.GetProductDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val detailUseCase: GetProductDetailUseCase) :
    BaseViewModel<ProductDetailUiState, Unit>(ProductDetailUiState()) {
    val product: MutableStateFlow<Product?> = MutableStateFlow(null)

    private fun getProductDetail(id: String) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        detailUseCase.invoke(id)
            .onSuccess { data ->
                product.update { data }
                updateUiState(uiState.value.copy(isLoading = false))
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
            }
    }
}