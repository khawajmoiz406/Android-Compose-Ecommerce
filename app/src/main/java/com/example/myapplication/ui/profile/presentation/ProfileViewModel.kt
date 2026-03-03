package com.example.myapplication.ui.profile.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.config.theme.ThemeState
import com.example.myapplication.core.model.User
import com.example.myapplication.ui.profile.domain.usecase.ChangeNotificationSettingUseCase
import com.example.myapplication.ui.profile.domain.usecase.ChangeThemeModeUseCase
import com.example.myapplication.ui.profile.domain.usecase.GetTotalOrdersUseCase
import com.example.myapplication.ui.profile.domain.usecase.GetUserInfoUseCase
import com.example.myapplication.ui.profile.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    getTotalOrdersUseCase: GetTotalOrdersUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changeThemeModeUseCase: ChangeThemeModeUseCase,
    private val changeNotificationSettingUseCase: ChangeNotificationSettingUseCase,
) : BaseViewModel<ProfileUiState, ProfileEvents>(ProfileUiState()) {
    val userProfile: MutableStateFlow<User?> = MutableStateFlow(null)
    val totalOrders = getTotalOrdersUseCase.invoke(Unit)
        .map { it ?: emptyList() } // Flow emits null if table is empty
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        getUserProfile()
    }

    fun getUserProfile() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = getUserInfoUseCase.invoke(Unit)
        if (result.isSuccess) {
            val data = result.getOrNull()
            userProfile.value = data
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }
    }

    fun changeThemeMode(themeMode: ThemeMode) = viewModelScope.launch {
        updateUiState(uiState.value.copy(loadingTheme = true))
        val result = changeThemeModeUseCase.invoke(themeMode)
        if (result.isSuccess) {
            val user = result.getOrNull()
            ThemeState.darkTheme.value = themeMode.value == ThemeMode.Dark.value
            userProfile.value = user
            updateUiState(uiState.value.copy(loadingTheme = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(loadingTheme = false, error = errorStr))
            events.emit(ProfileEvents.OnThemeChangeFailed(errorStr))
        }
    }

    fun changeNotificationEnabled(enabled: Boolean) = viewModelScope.launch {
        updateUiState(uiState.value.copy(loadingNotification = true))
        val result = changeNotificationSettingUseCase.invoke(enabled)
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(loadingNotification = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(loadingNotification = false, error = errorStr))
            events.emit(ProfileEvents.OnNotificationChangeFailed(errorStr))
        }
    }

    fun logout() = viewModelScope.launch {
        updateUiState(uiState.value.copy(loggingOut = true))
        val result = logoutUseCase.invoke(Unit)
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(loggingOut = false))
            events.emit(ProfileEvents.OnLogoutSuccess())
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(loadingTheme = false, error = errorStr))
            events.emit(ProfileEvents.OnLogoutFailed(errorStr))
        }
    }
}