package com.ecommerce.shoppy.ui.profile.presentation

sealed class ProfileEvents {
    class OnLogoutSuccess(): ProfileEvents()
    class OnThemeChangeFailed(val error: String): ProfileEvents()
    class OnNotificationChangeFailed(val error: String): ProfileEvents()
    class OnLogoutFailed(val error: String): ProfileEvents()
}