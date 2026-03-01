package com.example.myapplication.ui.address.presentation.listing

sealed class AddressListingEvents {
    class OnAddressDeleted() : AddressListingEvents()
    class OnError(val error: String) : AddressListingEvents()
}