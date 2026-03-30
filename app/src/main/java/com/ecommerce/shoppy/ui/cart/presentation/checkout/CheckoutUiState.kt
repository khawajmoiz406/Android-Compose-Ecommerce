package com.ecommerce.shoppy.ui.cart.presentation.checkout

import androidx.compose.runtime.Stable
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.state.FieldState

@Stable
data class CheckoutUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val promoLoading: Boolean = false,
    val placingOrder: Boolean = false,
    val termsAccepted: Boolean = false,
    val phoneNumberField: FieldState = FieldState(error = R.string.field_is_required)
)