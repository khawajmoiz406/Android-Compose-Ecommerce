package com.example.myapplication.ui.address.presentation.add

import androidx.compose.runtime.Stable
import com.example.myapplication.config.components.state.FieldState
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.AddressType

@Stable
data class NewAddressUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val defaultAddress: Boolean = true,
    val addressType: AddressType = AddressType.Home,
    val name: FieldState = FieldState(),
    val streetAddress: FieldState = FieldState(),
    val houseNo: FieldState = FieldState(),
    val city: FieldState = FieldState(),
    val state: FieldState = FieldState(),
    val zipcode: FieldState = FieldState(),
    val country: FieldState = FieldState(),
) {
    fun toAddress(id: Int = 0) = Address(
        id = id,
        type = addressType.value,
        fullName = name.value,
        address = streetAddress.value,
        houseNo = houseNo.value,
        city = city.value,
        state = state.value,
        country = country.value,
        zipCode = zipcode.value,
        defaultAddress = defaultAddress
    )
}