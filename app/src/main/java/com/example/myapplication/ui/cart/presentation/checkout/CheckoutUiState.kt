package com.example.myapplication.ui.cart.presentation.checkout

import androidx.compose.runtime.Stable
import com.example.myapplication.R
import com.example.myapplication.config.components.state.FieldState

@Stable
data class CheckoutUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
    val placingOrder: Boolean = false,
    val termsAccepted: Boolean = false,
    val phoneNumberField: FieldState = FieldState(error = R.string.field_is_required)
)