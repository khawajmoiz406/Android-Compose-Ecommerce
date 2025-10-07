package com.example.myapplication.ui.auth.login.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.core.remote.ApiException
import com.example.myapplication.models.request.LoginRequest
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) :
    BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()) {

    fun login() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val request = LoginRequest(uiState.value.email, uiState.value.password)

        loginUseCase.invoke(request)
            .onSuccess { data ->
                updateUiState(uiState.value.copy(isLoading = false))
                events.emit(LoginEvents.OnLoginSuccess(data))
            }
            .onFailure { error ->
                val errorStr = error.let { if (it is ApiException) it.error else it.localizedMessage ?: "" }
                updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
                events.emit(LoginEvents.OnLoginFailed(errorStr))
            }
    }
}