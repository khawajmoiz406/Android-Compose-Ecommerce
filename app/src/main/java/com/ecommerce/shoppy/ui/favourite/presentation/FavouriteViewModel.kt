package com.ecommerce.shoppy.ui.favourite.presentation

import androidx.lifecycle.viewModelScope
import com.ecommerce.shoppy.base.BaseViewModel
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.shared.domain.usecase.ToggleFavouriteUseCase
import com.ecommerce.shoppy.ui.favourite.domain.usecase.GetAllFavouriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val favouriteUseCase: GetAllFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) : BaseViewModel<FavouriteUiState, Unit>(FavouriteUiState()) {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        getAllFavourites()
    }

    fun getAllFavourites(fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = !fromSwipe, isRefreshing = fromSwipe))
        favouriteUseCase.invoke(Unit).collect { result ->
            _products.value = result ?: emptyList()
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
        }
    }

    fun toggleFavourite(product: Product) = viewModelScope.launch {
        val result = toggleFavouriteUseCase.invoke(product.id!!)
        if (result.isSuccess) {
            if (product.isFavourite) {
                _products.update { list -> list.filterNot { it.id == product.id } }
            }
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false, error = errorStr))
        }
    }
}