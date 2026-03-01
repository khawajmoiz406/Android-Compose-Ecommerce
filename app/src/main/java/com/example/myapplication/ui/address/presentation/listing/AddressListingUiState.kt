package com.example.myapplication.ui.address.presentation.listing

import androidx.compose.runtime.Stable

@Stable
data class AddressListingUiState(
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val selectedAddress: Int = 0,
)