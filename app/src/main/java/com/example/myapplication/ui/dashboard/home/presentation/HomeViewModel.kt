package com.example.myapplication.ui.dashboard.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.ui.dashboard.home.domain.GetHomeUseCase
import com.example.myapplication.ui.dashboard.home.domain.GetProductsByCategoryUseCase
import com.example.myapplication.ui.dashboard.home.domain.SearchProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: GetHomeUseCase,
    private val searchProductUseCase: SearchProductUseCase,
    private val productsUseCase: GetProductsByCategoryUseCase,
) :
    BaseViewModel<HomeUiState, Unit>(HomeUiState()) {
    val homeResponse: MutableStateFlow<HomeResponse?> = MutableStateFlow(null)

    init {
        getHome()
    }

    private fun getHome() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        homeUseCase.invoke(Unit)
            .onSuccess { data ->
                homeResponse.update { data }
                updateUiState(uiState.value.copy(isLoading = false))
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
            }
    }

    fun getProductsByCategory(category: String, fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = !fromSwipe, isRefreshing = fromSwipe))
        productsUseCase.invoke(category)
            .onSuccess { data ->
                homeResponse.update { home -> home?.apply { products = (data?.products ?: emptyList()) } }
                updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false, error = errorStr))
            }
    }

    fun searchProduct(str: String) = viewModelScope.launch {
        searchProductUseCase.invoke(str)
            .onSuccess { data ->
                homeResponse.update { home -> home?.copy(products = (data?.products ?: emptyList())) }
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false, error = errorStr))
            }
    }
}