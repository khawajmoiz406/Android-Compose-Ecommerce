package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LandingGraph.route
    ) {
        landingGraph(navController)
        authGraph(navController)
        bottomNavGraph(navController)
        drawerGraph(navController)
    }
}