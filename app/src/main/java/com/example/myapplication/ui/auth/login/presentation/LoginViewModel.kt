package com.example.myapplication.ui.auth.login.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()) {

    fun login() = viewModelScope.launch {
        uiState.update { it.copy(isLoading = true) }
        val request = LoginRequest(uiState.value.email, uiState.value.password)

        loginUseCase.invoke(request)
            .onSuccess { data ->
                uiState.update { it.copy(isLoading = false) }
                events.emit(LoginEvents.OnLoginSuccess(data))
            }
            .onFailure { error ->
                val errorStr = error.localizedMessage ?: ""
                uiState.update { it.copy(isLoading = false, error = errorStr) }
                events.emit(LoginEvents.OnLoginFailed(errorStr))
            }
    }
}