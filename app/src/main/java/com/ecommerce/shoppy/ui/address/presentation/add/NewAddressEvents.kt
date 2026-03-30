package com.ecommerce.shoppy.ui.address.presentation.add

sealed class NewAddressEvents {
    class OnAddressAdded() : NewAddressEvents()
    class OnAddressUpdated() : NewAddressEvents()
    class OnError(val error: String) : NewAddressEvents()
}