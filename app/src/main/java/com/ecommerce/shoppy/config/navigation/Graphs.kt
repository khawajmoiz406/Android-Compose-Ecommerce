package com.ecommerce.shoppy.config.navigation

import SplashScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ecommerce.shoppy.config.utils.extension.toNavType
import com.ecommerce.shoppy.core.model.Cart
import com.ecommerce.shoppy.core.model.PromoCode
import com.ecommerce.shoppy.ui.address.presentation.add.NewAddressScreen
import com.ecommerce.shoppy.ui.address.presentation.listing.AddressListingScreen
import com.ecommerce.shoppy.ui.auth.presentation.login.LoginScreen
import com.ecommerce.shoppy.ui.cart.presentation.cart.CartScreen
import com.ecommerce.shoppy.ui.cart.presentation.checkout.CheckoutScreen
import com.ecommerce.shoppy.ui.dashboard.presentation.DashboardScreen
import com.ecommerce.shoppy.ui.favourite.presentation.FavouriteScreen
import com.ecommerce.shoppy.ui.home.presentation.HomeScreen
import com.ecommerce.shoppy.ui.order.presentation.detail.OrderDetailScreen
import com.ecommerce.shoppy.ui.order.presentation.listing.OrdersScreen
import com.ecommerce.shoppy.ui.product_detail.presentation.ProductDetailScreen
import com.ecommerce.shoppy.ui.profile.presentation.ProfileScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.landingGraph() = navigation<Destination.LandingGraph>(
    startDestination = Destination.Splash
) {
    composable<Destination.Splash> { SplashScreen() }
    composable<Destination.Intro> { }
}

fun NavGraphBuilder.authGraph() = navigation<Destination.AuthGraph>(
    startDestination = Destination.Login
) {
    composable<Destination.Login> { LoginScreen() }
    composable<Destination.Signup> { }
}

fun NavGraphBuilder.bottomNavGraph() = navigation<Destination.BottomGraph>(
    startDestination = Destination.Home
) {
    composable<Destination.Home> { HomeScreen() }
    composable<Destination.Favourites> { FavouriteScreen() }
    composable<Destination.Profile> { ProfileScreen() }
}

fun NavGraphBuilder.drawerGraph() = navigation<Destination.DrawerGraph>(
    startDestination = Destination.Dashboard
) {
    composable<Destination.Dashboard> { DashboardScreen() }
    composable<Destination.AboutUs> { }
    composable<Destination.PrivacyPolicy> { }
    composable<Destination.Settings> { }
    composable<Destination.CartScreen> { CartScreen() }
    composable<Destination.OrderListing> { OrdersScreen() }

    composable<Destination.OrderDetail> {
        val route = it.toRoute<Destination.OrderDetail>()
        OrderDetailScreen(orderId = route.orderId)
    }

    composable<Destination.AddressListing> {
        val route = it.toRoute<Destination.AddressListing>()
        AddressListingScreen(selectionMode = route.selectionMode)
    }

    composable<Destination.NewAddress> {
        val route = it.toRoute<Destination.NewAddress>()
        NewAddressScreen(addressId = route.id)
    }

    composable<Destination.ProductDetail> {
        val route = it.toRoute<Destination.ProductDetail>()
        ProductDetailScreen(productId = route.id)
    }

    composable<Destination.Checkout>(
        typeMap = mapOf(
            typeOf<Cart>() to toNavType<Cart>(),
            typeOf<PromoCode?>() to toNavType<PromoCode>(nullable = true),
        )
    ) {
        val route = it.toRoute<Destination.Checkout>()
        CheckoutScreen(cart = route.cart, promoCode = route.promoCode)
    }
}