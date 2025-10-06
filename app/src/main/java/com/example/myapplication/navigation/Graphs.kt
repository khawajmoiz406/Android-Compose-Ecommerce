package com.example.myapplication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.landingGraph(navController: NavController) = navigation(
    route = Destinations.LandingGraph.route,
    startDestination = Destinations.Splash.route
) {
    composable(Destinations.Splash.route) {}
    composable(Destinations.Intro.route) {}
}

fun NavGraphBuilder.authGraph(navController: NavController) = navigation(
    route = Destinations.AuthGraph.route,
    startDestination = Destinations.Login.route
) {
    composable(Destinations.Login.route) {}
    composable(Destinations.Signup.route) {}
}

fun NavGraphBuilder.bottomNavGraph(navController: NavController) = navigation(
    route = Destinations.BottomGraph.route,
    startDestination = Destinations.Home.route
) {
    composable(Destinations.Home.route) {}
    composable(Destinations.Favourites.route) {}
    composable(Destinations.Profile.route) {}
}

fun NavGraphBuilder.drawerGraph(navController: NavController) = navigation(
    route = Destinations.DrawerGraph.route,
    startDestination = Destinations.Dashboard.route
) {
    composable(Destinations.Dashboard.route) {}
    composable(Destinations.AboutUs.route) {}
    composable(Destinations.PrivacyPolicy.route) {}
    composable(Destinations.Settings.route) {}
}