package com.example.myapplication.ui.address.presentation.add

sealed class NewAddressEvents {
    class OnAddressAdded() : NewAddressEvents()
    class OnAddressUpdated() : NewAddressEvents()
    class OnError(val error: String) : NewAddressEvents()
}