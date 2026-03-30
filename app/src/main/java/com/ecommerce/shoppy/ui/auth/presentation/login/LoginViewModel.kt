package com.ecommerce.shoppy.ui.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.ecommerce.shoppy.base.BaseViewModel
import com.ecommerce.shoppy.core.remote.ApiException
import com.ecommerce.shoppy.ui.auth.data.remote.dto.LoginRequest
import com.ecommerce.shoppy.ui.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) :
    BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()) {

    fun login() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val request = LoginRequest(uiState.value.email, uiState.value.password)

        val result = loginUseCase.invoke(request)
        if (result.isSuccess) {
            val data = result.getOrNull()
            updateUiState(uiState.value.copy(isLoading = false))
            events.emit(LoginEvents.OnLoginSuccess(data!!))
        } else {
            val error = result.exceptionOrNull()
            val errorStr = if (error is ApiException) error.error else error?.localizedMessage ?: ""
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
            events.emit(LoginEvents.OnLoginFailed(errorStr))
        }

    }
}