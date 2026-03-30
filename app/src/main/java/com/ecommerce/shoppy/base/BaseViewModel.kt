package com.ecommerce.shoppy.base

import androidx.lifecycle.ViewModel
import com.ecommerce.shoppy.core.remote.ApiException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<U, E>(initialState: U) : ViewModel() {
    val uiState: MutableStateFlow<U> = MutableStateFlow(initialState)
    val events: MutableSharedFlow<E> = MutableSharedFlow()

    fun updateUiState(newUiState: U) {
        uiState.update { newUiState }
    }

    fun Throwable?.toErrorString(): String {
        return if (this is ApiException) this.error else this?.localizedMessage ?: ""
    }
}