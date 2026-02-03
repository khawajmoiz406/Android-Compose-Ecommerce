package com.example.myapplication.ui.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.model.Category
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest
import com.example.myapplication.core.shared.domain.usecase.GetCartCountUseCase
import com.example.myapplication.ui.home.domain.usecase.GetHomeUseCase
import com.example.myapplication.ui.home.domain.usecase.GetProductsByFiltersUseCase
import com.example.myapplication.ui.home.domain.usecase.ObserverProductsFavouriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    getCartCountUseCase: GetCartCountUseCase,
    private val homeUseCase: GetHomeUseCase,
    private val getProductsByFiltersUseCase: GetProductsByFiltersUseCase,
    private val observerProductsFavouriteUseCase: ObserverProductsFavouriteUseCase,
) : BaseViewModel<HomeUiState, Unit>(HomeUiState()) {
    var filters = ProductsRequest()
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()
    val cartCount = getCartCountUseCase.invoke(Unit)
        .map { it ?: 0 } // Flow emits null if table is empty
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    init {
        getHome()
        observeFavoriteChanges()
    }

    private fun observeFavoriteChanges() = viewModelScope.launch {
        observerProductsFavouriteUseCase.invoke(Unit).collect { favourites ->
            val favouritesMap = favourites?.associate { it.id to it.isFavorite } ?: emptyMap()
            _products.value = _products.value.map { product ->
                val newFavoriteStatus = favouritesMap[product.id] ?: product.isFavourite
                if (newFavoriteStatus != product.isFavourite) return@map product.copy(isFavourite = newFavoriteStatus)
                return@map product
            }
        }
    }

    fun getHome(fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(mainLoading = !fromSwipe, isRefreshing = fromSwipe))
        homeUseCase.invoke(filters).collect { result ->
            if (result.isSuccess) {
                val data = result.getOrNull()
                _products.update { data?.products ?: emptyList() }
                _categories.update { data?.categories ?: emptyList() }
                updateUiState(uiState.value.copy(mainLoading = false, isRefreshing = false))
            } else {
                val errorStr = result.exceptionOrNull().toErrorString()
                updateUiState(uiState.value.copy(mainLoading = false, isRefreshing = false, error = errorStr))
            }
        }
    }

    fun getProductsByCategory(category: String) = viewModelScope.launch {
        updateUiState(uiState.value.copy(categoryProductsLoading = true))
        val result = getProductsByFiltersUseCase.invoke(filters.copy(category = category).also { filters = it })
        if (result.isSuccess) {
            val data = result.getOrNull()
            _products.update { data ?: emptyList() }
            updateUiState(uiState.value.copy(categoryProductsLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(categoryProductsLoading = false, error = errorStr))
        }
    }

    fun searchProduct(str: String) = viewModelScope.launch {
        val result = getProductsByFiltersUseCase.invoke(filters.copy(search = str).also { filters = it })
        if (result.isSuccess) {
            val data = result.getOrNull()
            _products.update { data ?: emptyList() }
            updateUiState(uiState.value.copy(mainLoading = false, isRefreshing = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(mainLoading = false, isRefreshing = false, error = errorStr))
        }
    }

    fun filterProduct(request: ProductsRequest) = viewModelScope.launch {
        updateUiState(uiState.value.copy(categoryProductsLoading = true))
        _products.update { emptyList() }

        filters = filters.copy(order = request.order, sortBy = request.sortBy)
        val result = getProductsByFiltersUseCase.invoke(filters)
        if (result.isSuccess) {
            val data = result.getOrNull()
            _products.update { data ?: emptyList() }
            updateUiState(uiState.value.copy(categoryProductsLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(categoryProductsLoading = false, error = errorStr))
        }
    }
}