package com.example.myapplication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.myapplication.ui.auth.login.presentation.LoginScreen
import com.example.myapplication.ui.dashboard.DashboardScreen
import com.example.myapplication.ui.dashboard.home.presentation.HomeScreen
import com.example.myapplication.ui.landing.splash.presentation.SplashScreen

fun NavGraphBuilder.landingGraph(navController: NavController) = navigation(
    route = Destinations.LandingGraph.route,
    startDestination = Destinations.Splash.route
) {
    composable(Destinations.Splash.route) { SplashScreen(navController) }
    composable(Destinations.Intro.route) {}
}

fun NavGraphBuilder.authGraph(navController: NavController) = navigation(
    route = Destinations.AuthGraph.route,
    startDestination = Destinations.Login.route
) {
    composable(Destinations.Login.route) { LoginScreen(navController) }
    composable(Destinations.Signup.route) {}
}

fun NavGraphBuilder.bottomNavGraph(navController: NavController) = navigation(
    route = Destinations.BottomGraph.route,
    startDestination = Destinations.Home.route
) {
    composable(Destinations.Home.route) { HomeScreen(navController) }
    composable(Destinations.Favourites.route) {}
    composable(Destinations.Profile.route) {}
}

fun NavGraphBuilder.drawerGraph(navController: NavController) = navigation(
    route = Destinations.DrawerGraph.route,
    startDestination = Destinations.Dashboard.route
) {
    composable(Destinations.Dashboard.route) { DashboardScreen() }
    composable(Destinations.AboutUs.route) {}
    composable(Destinations.PrivacyPolicy.route) {}
    composable(Destinations.Settings.route) {}
}