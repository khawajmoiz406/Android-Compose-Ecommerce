package com.example.myapplication.config.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.ui.auth.presentation.login.LoginScreen
import com.example.myapplication.ui.cart.presentation.cart.CartScreen
import com.example.myapplication.ui.cart.presentation.checkout.CheckoutScreen
import com.example.myapplication.ui.dashboard.presentation.DashboardScreen
import com.example.myapplication.ui.favourite.presentation.FavouriteScreen
import com.example.myapplication.ui.home.presentation.HomeScreen
import com.example.myapplication.ui.landing.splash.presentation.SplashScreen
import com.example.myapplication.ui.product_detail.presentation.ProductDetailScreen

fun NavGraphBuilder.landingGraph() = navigation(
    route = Destinations.LandingGraph.route,
    startDestination = Destinations.Splash.route
) {
    composable(Destinations.Splash.route) { SplashScreen() }
    composable(Destinations.Intro.route) {}
}

fun NavGraphBuilder.authGraph() = navigation(
    route = Destinations.AuthGraph.route,
    startDestination = Destinations.Login.route
) {
    composable(Destinations.Login.route) { LoginScreen() }
    composable(Destinations.Signup.route) {}
}

fun NavGraphBuilder.bottomNavGraph() = navigation(
    route = Destinations.BottomGraph.route,
    startDestination = Destinations.Home.route
) {
    composable(Destinations.Home.route) { HomeScreen() }
    composable(Destinations.Favourites.route) { FavouriteScreen() }
    composable(Destinations.Profile.route) {}
}

@Suppress("DEPRECATION")
fun NavGraphBuilder.drawerGraph(navController: NavController) = navigation(
    route = Destinations.DrawerGraph.route,
    startDestination = Destinations.Dashboard.route
) {
    composable(Destinations.Dashboard.route) { DashboardScreen() }
    composable(Destinations.AboutUs.route) {}
    composable(Destinations.PrivacyPolicy.route) {}
    composable(Destinations.Settings.route) {}
    composable(Destinations.Cart.route) { CartScreen() }
    composable(Destinations.Checkout.route) {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        val cart = savedStateHandle?.get<Cart>("cart") ?: return@composable
        val promoCode = savedStateHandle.get<PromoCode?>("promoCode")
        CheckoutScreen(cart = cart, promoCode = promoCode)
    }
    composable(Destinations.ProductDetail.route, Destinations.ProductDetail.arguments) { bse ->
        ProductDetailScreen(productId = bse.arguments?.getInt("id")!!)
    }
}