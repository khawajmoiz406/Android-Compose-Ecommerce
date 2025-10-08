package com.example.myapplication.navigation

sealed class Destinations(val route: String) {
    //Landing routes
    data object LandingGraph: Destinations("landing_graph")
    data object Splash : Destinations("splash")
    data object Intro : Destinations("intro")

    //Authentication routes
    data object AuthGraph: Destinations("auth_graph")
    data object Login : Destinations("login")
    data object Signup : Destinations("signup")

    //Bottom nav routes
    data object BottomGraph : Destinations("bottom_nav_graph")
    data object Home : Destinations("home")
    data object Favourites : Destinations("favourites")
    data object Profile : Destinations("profile")

    //Drawer routes
    data object DrawerGraph : Destinations("drawer_graph")
    data object Dashboard : Destinations("dashboard")
    data object AboutUs : Destinations("about_us")
    data object PrivacyPolicy : Destinations("privacy_policy")
    data object Settings : Destinations("settings")

    //Other routes
    data object ProductDetail : Destinations("product_detail/{id}") {
        fun createRoute(id: Int) = "product_detail/$id"
    }
}