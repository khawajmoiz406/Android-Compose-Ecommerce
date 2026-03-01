package com.example.myapplication.ui.cart.presentation.checkout

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
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
) : BaseViewModel<CheckoutUiState, Unit>(CheckoutUiState()) {
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
                shippingAddress = defaultAddress
            )
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }
    }

    fun checkout() {
        if (checkoutRequest.value == null) return
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
}