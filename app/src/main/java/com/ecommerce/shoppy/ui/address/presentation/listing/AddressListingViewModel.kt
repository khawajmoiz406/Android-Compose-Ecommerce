package com.ecommerce.shoppy.ui.address.presentation.listing

import androidx.lifecycle.viewModelScope
import com.ecommerce.shoppy.base.BaseViewModel
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.ui.address.domain.usecase.DeleteAddressUseCase
import com.ecommerce.shoppy.ui.address.domain.usecase.GetUserAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressListingViewModel(
    private val addressUseCase: GetUserAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
) : BaseViewModel<AddressListingUiState, AddressListingEvents>(AddressListingUiState()) {
    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> = _addresses.asStateFlow()

    init {
        getUserAddresses()
    }

    fun getUserAddresses(fromSwipe: Boolean = false) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = !fromSwipe, isRefreshing = fromSwipe))
        addressUseCase.invoke(Unit).collect { result ->
            _addresses.value = result ?: emptyList()
            updateUiState(uiState.value.copy(isLoading = false, isRefreshing = false))
        }
    }

    fun onSelectionChanged(selection: Int) = viewModelScope.launch {
        updateUiState(uiState.value.copy(selectedAddress = selection))
    }

    fun deleteAddress(id: Int) = viewModelScope.launch {
        updateUiState(uiState.value.copy(isDeleting = true))
        val result = deleteAddressUseCase.invoke(id)
        if (result.isSuccess) {
            updateUiState(uiState.value.copy(isDeleting = false))
            events.emit(AddressListingEvents.OnAddressDeleted())
        } else {
            val errorStr = result.exceptionOrNull().toErrorString()
            updateUiState(uiState.value.copy(isDeleting = false, error = errorStr))
            events.emit(AddressListingEvents.OnError(errorStr))
        }
    }
}