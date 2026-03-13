package com.example.myapplication.config.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.myapplication.config.utils.extension.toNavType
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.ui.address.presentation.add.NewAddressScreen
import com.example.myapplication.ui.address.presentation.listing.AddressListingScreen
import com.example.myapplication.ui.auth.presentation.login.LoginScreen
import com.example.myapplication.ui.cart.presentation.cart.CartScreen
import com.example.myapplication.ui.cart.presentation.checkout.CheckoutScreen
import com.example.myapplication.ui.dashboard.presentation.DashboardScreen
import com.example.myapplication.ui.favourite.presentation.FavouriteScreen
import com.example.myapplication.ui.home.presentation.HomeScreen
import com.example.myapplication.ui.landing.splash.presentation.SplashScreen
import com.example.myapplication.ui.order.presentation.detail.OrderDetailScreen
import com.example.myapplication.ui.order.presentation.listing.OrdersScreen
import com.example.myapplication.ui.product_detail.presentation.ProductDetailScreen
import com.example.myapplication.ui.profile.presentation.ProfileScreen
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