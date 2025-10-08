package com.example.myapplication.ui.dashboard.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.ui.dashboard.home.domain.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val productsUseCase: GetProductsUseCase) :
    BaseViewModel<HomeUiState, Unit>(HomeUiState()) {
    val products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())

    init {
        getProducts()
    }

    fun getProducts(fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true, isRefreshing = fromSwipe))
        productsUseCase.invoke(Unit)
            .onSuccess { data ->
                updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
                products.update { data?.products ?: emptyList() }
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false, error = errorStr))
            }
    }
}