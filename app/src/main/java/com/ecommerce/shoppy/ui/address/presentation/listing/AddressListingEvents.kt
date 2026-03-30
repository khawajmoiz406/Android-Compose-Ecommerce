package com.ecommerce.shoppy.ui.address.presentation.listing

sealed class AddressListingEvents {
    class OnAddressDeleted() : AddressListingEvents()
    class OnError(val error: String) : AddressListingEvents()
}