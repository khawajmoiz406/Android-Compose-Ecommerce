package com.ecommerce.shoppy.ui.address.presentation.add

import androidx.lifecycle.viewModelScope
import com.ecommerce.shoppy.base.BaseViewModel
import com.ecommerce.shoppy.config.components.state.FieldState
import com.ecommerce.shoppy.config.utils.validator.GenericValidators
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.AddressType
import com.ecommerce.shoppy.ui.address.domain.usecase.GetAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.NewAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.UpdateAddressUseCase
import kotlinx.coroutines.launch

class NewAddressViewModel(
    private val getAddressUseCase: GetAddressUseCase,
    private val newAddressUseCase: NewAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
) : BaseViewModel<NewAddressUiState, NewAddressEvents>(NewAddressUiState()) {
    var address: Address? = null

    fun getUserAddress(id: Int) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        val result = getAddressUseCase.invoke(id)
        if (result.isSuccess) {
            val data = result.getOrNull()
            address = data
            init()
            updateUiState(uiState.value.copy(isLoading = false))
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isLoading = false, error = errorStr))
        }
    }

    fun init() = address?.let {
        updateUiState(
            uiState.value.copy(
                name = FieldState(value = it.fullName),
                city = FieldState(value = it.city),
                zipcode = FieldState(value = it.zipCode),
                state = FieldState(value = it.state),
                streetAddress = FieldState(value = it.address),
                country = FieldState(value = it.country),
                houseNo = FieldState(value = it.houseNo ?: ""),
                addressType = it.getAddressType(),
                defaultAddress = it.defaultAddress
            )
        )
    }

    fun addNewAddress() = viewModelScope.launch {
        if (!validateForm()) return@launch
        updateUiState(uiState.value.copy(isSaving = true))
        val result = newAddressUseCase.invoke(uiState.value.toAddress())
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(isSaving = false))
            events.emit(NewAddressEvents.OnAddressAdded())
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isSaving = false, error = errorStr))
            events.emit(NewAddressEvents.OnError(errorStr))
        }
    }

    fun updateAddress() = viewModelScope.launch {
        if (!validateForm()) return@launch
        updateUiState(uiState.value.copy(isSaving = true))
        val result = updateAddressUseCase.invoke(uiState.value.toAddress(address?.id!!))
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(isSaving = false))
            events.emit(NewAddressEvents.OnAddressUpdated())
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isSaving = false, error = errorStr))
            events.emit(NewAddressEvents.OnError(errorStr))
        }
    }

    fun toggleDefaultAddress() =
        updateUiState(uiState.value.copy(defaultAddress = !uiState.value.defaultAddress))

    fun onAddressTypeChange(value: AddressType) =
        updateUiState(uiState.value.copy(addressType = value))

    fun onFieldChange(value: String, fieldUpdater: NewAddressUiState.(FieldState) -> NewAddressUiState) {
        updateUiState(uiState.value.fieldUpdater(FieldState(value = value)))
    }

    fun validateForm(): Boolean {
        val validated = uiState.value.copy(
            name = uiState.value.name.copy(
                error = GenericValidators.validateField(uiState.value.name.value, maxLength = 50),
                isTouched = true
            ),
            streetAddress = uiState.value.streetAddress.copy(
                error = GenericValidators.validateField(uiState.value.streetAddress.value),
                isTouched = true
            ),
            city = uiState.value.city.copy(
                error = GenericValidators.validateField(uiState.value.city.value),
                isTouched = true
            ),
            state = uiState.value.state.copy(
                error = GenericValidators.validateField(uiState.value.state.value),
                isTouched = true
            ),
            zipcode = uiState.value.zipcode.copy(
                error = GenericValidators.validateField(uiState.value.zipcode.value),
                isTouched = true
            ),
            country = uiState.value.country.copy(
                error = GenericValidators.validateField(uiState.value.country.value),
                isTouched = true
            ),
        )
        updateUiState(validated)

        return listOf(
            validated.name,
            validated.streetAddress,
            validated.city,
            validated.state,
            validated.zipcode,
            validated.country
        ).all { it.error == null }
    }

    fun resetForm() = updateUiState(
        uiState.value.copy(
            name = FieldState(),
            city = FieldState(),
            state = FieldState(),
            houseNo = FieldState(),
            zipcode = FieldState(),
            country = FieldState(),
            streetAddress = FieldState(),
        )
    )
}