package com.example.myapplication.ui.cart.presentation.checkout

import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.components.state.FieldState
import com.example.myapplication.config.utils.validator.GenericValidators
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PaymentMethod
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.core.model.Shipping
import com.example.myapplication.ui.cart.data.remote.dto.CheckoutRequest
import com.example.myapplication.ui.cart.domain.usecase.CheckoutUseCase
import com.example.myapplication.ui.cart.domain.usecase.GetDefaultAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val checkoutUseCase: CheckoutUseCase,
    private val getDefaultAddressUseCase: GetDefaultAddressUseCase,
) : BaseViewModel<CheckoutUiState, CheckoutEvents>(CheckoutUiState()) {
    var shippingMethods: List<Shipping> = listOf()
    val checkoutRequest: MutableStateFlow<CheckoutRequest?> = MutableStateFlow(null)

    fun initCheckout(cart: Cart, promoCode: PromoCode?) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = getDefaultAddressUseCase.invoke(Unit)
        if (result.isSuccess) {
            val defaultAddress = result.getOrNull()
            shippingMethods = listOf(
                Shipping(1, "Standard Shipping", "5-7 business days", 25.0),
                Shipping(2, "Express Shipping", "2-3 business days", 35.0),
                Shipping(3, "Overnight Shipping", "Next business days", 50.0),
            )
            checkoutRequest.value = CheckoutRequest(
                cart = cart,
                promoCode = promoCode,
                shippingMethod = shippingMethods.first(),
                paymentMethod = PaymentMethod.CashOnDelivery,
                shippingAddress = defaultAddress,
                phoneNumber = ""
            )
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }
    }

    fun checkout() = viewModelScope.launch {
        updateUiState(uiState.value.copy(placingOrder = true))
        if (checkoutRequest.value == null) return@launch
        val result = checkoutUseCase.invoke(checkoutRequest.value!!)
        if (result.isSuccess) {
            val orderId = result.getOrNull()
                ?: return@launch events.emit(CheckoutEvents.OnError("Error while placing order"))
            updateUiState(uiState.value.copy(placingOrder = false))
            events.emit(CheckoutEvents.OnOrderPlaced(orderId))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(placingOrder = false))
            events.emit(CheckoutEvents.OnError(errorStr))
        }
    }

    fun shippingChanged(shipping: Shipping) {
        checkoutRequest.value = checkoutRequest.value?.copy(shippingMethod = shipping)
    }

    fun paymentMethodChanged(method: PaymentMethod) {
        checkoutRequest.value = checkoutRequest.value?.copy(paymentMethod = method)
    }

    fun onShippingAddressChanged(address: Address) {
        checkoutRequest.value = checkoutRequest.value?.copy(shippingAddress = address)
    }

    fun onPhoneNumberChanged(number: String) {
        val field = when {
            number.isEmpty() -> FieldState(value = number, error = R.string.field_is_required)

            !GenericValidators.validatePhoneNumber(number) -> FieldState(
                value = number,
                error = R.string.invalid_phone_number
            )

            else -> {
                checkoutRequest.value = checkoutRequest.value?.copy(phoneNumber = number)
                FieldState(number)
            }
        }
        updateUiState(uiState.value.copy(phoneNumberField = field))
    }

    fun toggleTerms() {
        updateUiState(uiState.value.copy(termsAccepted = !uiState.value.termsAccepted))
    }
}